package com.lab.greenpremium.data.local

import android.content.Context
import com.lab.greenpremium.NO_DATA
import devliving.online.securedpreferencestore.DefaultRecoveryHandler
import devliving.online.securedpreferencestore.SecuredPreferenceStore
import javax.inject.Inject

const val KEY_AUTH_TOKEN = "key_auth_token"

class PreferencesManager @Inject constructor(val context: Context) {

    private val preferences: SecuredPreferenceStore by lazy {
        SecuredPreferenceStore.init(context, DefaultRecoveryHandler())
        SecuredPreferenceStore.getSharedInstance()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    private operator fun contains(key: String): Boolean {
        return preferences.contains(key)
    }

    //region get/put methods
    private fun getString(key: String): String {
        return preferences.getString(key, "")
    }

    private fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    private fun getInt(key: String): Int {
        return preferences.getInt(key, NO_DATA)
    }

    private fun putInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    private fun getLong(key: String): Long {
        return preferences.getLong(key, NO_DATA.toLong())
    }

    private fun putLong(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    private fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    private fun putBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }
    //endregion

    fun setToken(token: String) {
        putString(KEY_AUTH_TOKEN, token)
    }

    fun getToken() : String {
        return getString(KEY_AUTH_TOKEN)
    }

}