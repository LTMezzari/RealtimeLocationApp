package mezzari.torres.lucas.realtimelocationapp.flow.access

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_access.*
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.realtimelocationapp.R
import mezzari.torres.lucas.realtimelocationapp.archive.bindTo
import mezzari.torres.lucas.realtimelocationapp.flow.MainConductor

class AccessActivity : BaseActivity() {
    override val conductor = MainConductor

    val viewModel: AccessActivityViewModel by lazy {
        AccessActivityViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access)

        etUsername.bindTo(viewModel.username) {
            btnNext.isEnabled = !it.isNullOrEmpty() && !etUsername.text.isNullOrEmpty()
        }

        etIp.bindTo(viewModel.ipAddress) {
            btnNext.isEnabled = !it.isNullOrEmpty() && !etUsername.text.isNullOrEmpty()
        }

        btnNext.setOnClickListener {
            MainConductor.ipAddress = viewModel.ipAddress.value ?: ""
            MainConductor.username = viewModel.username.value ?: ""
            next()
        }
    }
}
