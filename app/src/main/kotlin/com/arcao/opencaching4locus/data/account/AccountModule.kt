package com.arcao.opencaching4locus.data.account

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AccountModule {
    @Provides
    fun providesAccountManager(context: Context): AccountManager {
        return AccountManager.get(context)
    }

    @Provides
    @ForAccountType(AccountType.OPENCACHING_PL)
    fun providesAccountPL(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_PL)
    }

    @Provides
    @ForAccountType(AccountType.OPENCACHING_DE)
    fun providesAccountDE(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_DE)
    }

    @Provides
    @ForAccountType(AccountType.OPENCACHING_US)
    fun providesAccountUS(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_US)
    }

    @Provides
    @ForAccountType(AccountType.OPENCACHING_NL)
    fun providesAccountNL(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_NL)
    }

    @Provides
    @ForAccountType(AccountType.OPENCACHING_RO)
    fun providesAccountRO(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_RO)
    }

    @Provides
    @ForAccountType(AccountType.OPENCACHING_UK)
    fun providesAccountUK(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_UK)
    }
}