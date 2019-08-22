package mezzari.torres.lucas.realtimelocationapp.archive

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author Lucas T. Mezzari
 * @since 22/08/2019
 **/
fun <T> MutableLiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(lifecycleOwner, Observer {
        observer(it)
    })
}

fun EditText.addAfterTextChangedListener(listener: (Editable?) -> Unit) {
    addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            listener(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    })
}

fun EditText.bindTo(property: MutableLiveData<String>, observer: ((String?) -> Unit)? = null) {
    addAfterTextChangedListener { editable ->
        if (property.value != editable.toString()) {
            property.value = editable.toString()
            observer?.invoke(property.value)
        }
    }
    property.observe((context as AppCompatActivity)){ value ->
        if (property.value != this.text.toString()) {
            setText(value)
            observer?.invoke(value)
        }
    }
}