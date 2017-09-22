package com.arcao.opencaching4locus.data.account

import android.content.Context

class AccountManager private constructor(private val context: Context) {
    val authenticatedAccounts: Collection<Account> get() = AccountType.values().map {
        getAccount(it)
    }.filter { it.authenticated }

    companion object {
        fun get(context: Context): AccountManager {
            return AccountManager(context.applicationContext)
        }

        private val accounts = HashMap<AccountType, Account>()
    }

    fun getAccount(accountType: AccountType): Account {
        var account = accounts[accountType]

        if (account == null) {
            account = Account(context, accountType)
            accounts[accountType] = account
        }

        return account
    }

    fun authenticated(accountType: AccountType): Boolean = getAccount(accountType).authenticated
}