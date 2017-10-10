package com.arcao.opencaching4locus.data.account

import android.content.Context
import android.content.SharedPreferences
import com.arcao.opencaching4locus.model.response.User
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuth1RequestToken

class Account(context: Context, val accountType: AccountType) {
    companion object {
        private const val PREF_TOKEN = "TOKEN"
        private const val PREF_SECRET = "SECRET"
        private const val PREF_USERNAME = "USERNAME"
        private const val PREF_REQUEST_TOKEN = "REQUEST_TOKEN"
        private const val PREF_REQUEST_SECRET = "REQUEST_SECRET"
        private const val PREF_LIVE_MAP_ENABLED = "LIVE_MAP_ENABLED"
        private const val PREF_LIVE_MAP_ENABLED_DEFAULT = true
    }

    private val preferences: SharedPreferences = context.getSharedPreferences("ACCOUNT_${accountType.name}", Context.MODE_PRIVATE)


    val authenticated: Boolean get() = preferences.contains(PREF_TOKEN)
    val accessToken: String? get() = preferences.getString(PREF_TOKEN, null)
    val accessSecret: String? get() = preferences.getString(PREF_SECRET, null)
    val userName: String? get() = preferences.getString(PREF_USERNAME, null)

    fun authorize(token: OAuth1AccessToken) {
        preferences.edit().apply {
            putString(PREF_TOKEN, token.token)
            putString(PREF_SECRET, token.tokenSecret)
        }.apply()
    }

    fun updateUser(user: User) {
        preferences.edit().apply {
            putString(PREF_USERNAME, user.username)
        }.apply()
    }

    fun remove() {
        preferences.edit().apply {
            remove(PREF_TOKEN)
            remove(PREF_SECRET)
            remove(PREF_USERNAME)
        }.apply()
    }

    var requestToken: OAuth1RequestToken
        get() {
            val token = OAuth1RequestToken(preferences.getString(PREF_REQUEST_TOKEN, null), preferences.getString(PREF_REQUEST_SECRET, null))
            preferences.edit().apply {
                remove(PREF_REQUEST_TOKEN)
                remove(PREF_REQUEST_SECRET)
            }.apply()
            return token
        }
        set(value) {
            preferences.edit().apply {
                putString(PREF_REQUEST_TOKEN, value.token)
                putString(PREF_REQUEST_SECRET, value.tokenSecret)
            }.apply()
        }

    val hasRequestToken: Boolean get() = preferences.contains(PREF_REQUEST_TOKEN) && preferences.contains(PREF_REQUEST_SECRET)

    var liveMapEnabled: Boolean
        get() = preferences.getBoolean(PREF_LIVE_MAP_ENABLED, PREF_LIVE_MAP_ENABLED_DEFAULT)
        set(value) = preferences.edit().putBoolean(PREF_LIVE_MAP_ENABLED, value).apply()

}