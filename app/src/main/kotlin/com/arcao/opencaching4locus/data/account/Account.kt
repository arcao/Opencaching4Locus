package com.arcao.opencaching4locus.data.account

import android.content.Context
import android.content.SharedPreferences
import com.arcao.opencaching4locus.model.response.User
import com.github.scribejava.core.model.OAuth1AccessToken

class Account(private val context : Context, val accountType: AccountType) {
    val preferences : SharedPreferences = context.getSharedPreferences("ACCOUNT_${accountType.name}", Context.MODE_PRIVATE)

    fun authenticated() : Boolean {
        return false
    }

    fun accessToken(): String? {
        return null
    }

    fun accessSecret(): String? {
        return null
    }

    fun authorize(token: OAuth1AccessToken, user: User) {
        preferences.edit().apply {
            putString("TOKEN", token.token)
            putString("SECRET", token.tokenSecret)
            putString("USERNAME", user.username)
        }.apply()
    }
}