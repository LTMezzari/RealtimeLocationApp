package mezzari.torres.lucas.realtimelocationapp.flow.access

import androidx.lifecycle.MutableLiveData

/**
 * @author Lucas T. Mezzari
 * @since 22/08/2019
 **/
class AccessActivityViewModel {
    val ipAddress = MutableLiveData<String>()
    val username = MutableLiveData<String>()
}