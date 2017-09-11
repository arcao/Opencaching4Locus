package com.arcao.opencaching4locus.data.account

import android.content.Context

class AccountManager private constructor(private val context: Context) {
    companion object {
        fun get(context: Context) : AccountManager {
            return AccountManager(context)
        }
    }

    fun getAccount(accountType: AccountType) : Account {
        return Account(context, accountType)
    }
}