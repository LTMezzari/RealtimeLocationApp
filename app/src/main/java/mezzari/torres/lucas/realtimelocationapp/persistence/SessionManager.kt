package mezzari.torres.lucas.realtimelocationapp.persistence

import android.content.Context
import android.content.SharedPreferences

/**
 * @author Lucas T. Mezzari
 * @since 22/08/2019
 **/
object SessionManager {
    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        this.sharedPreferences = context.getSharedPreferences(javaClass.name, Context.MODE_PRIVATE)
    }

    var ipAddress: String get() {
        return sharedPreferences.getString("ip_address", "") ?: ""
    } set(value) {
        sharedPreferences.edit().apply {
            putString("ip_address", value)
        }.apply()
    }

    var username: String get() {
        return sharedPreferences.getString("username", "") ?: ""
    } set(value) {
        sharedPreferences.edit().apply {
            putString("username", value)
        }.apply()
    }
}