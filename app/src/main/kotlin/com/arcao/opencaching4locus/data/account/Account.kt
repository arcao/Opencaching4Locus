package com.arcao.opencaching4locus.data.account

import android.content.Context

class Account(private val context : Context, val accountType: AccountType) {

    fun isAuthenticated() : Boolean {
        return false
    }
}