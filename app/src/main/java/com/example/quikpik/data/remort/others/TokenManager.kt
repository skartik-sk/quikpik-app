package com.example.quikpik.data.remort.others

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_TOKEN = "auth_token"
    }

    fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun deleteToken() {
        sharedPreferences.edit().remove(KEY_TOKEN).commit()

        val tokenAfter = getToken()
        Log.d("token", "token afver del $tokenAfter")
    }
}