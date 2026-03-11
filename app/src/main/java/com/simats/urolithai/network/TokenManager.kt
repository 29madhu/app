package com.simats.urolithai.network

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("UroLithAIPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
    }

    fun saveTokens(access: String, refresh: String) {
        prefs.edit().apply {
            putString(ACCESS_TOKEN_KEY, access)
            putString(REFRESH_TOKEN_KEY, refresh)
            apply()
        }
    }

    fun getAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN_KEY, null)
    }

    fun clearTokens() {
        prefs.edit().apply {
            remove(ACCESS_TOKEN_KEY)
            remove(REFRESH_TOKEN_KEY)
            apply()
        }
    }
}
