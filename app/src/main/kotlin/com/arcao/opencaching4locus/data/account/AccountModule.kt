package com.arcao.opencaching4locus.data.account

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
object AccountModule {
    @JvmStatic
    @Provides
    fun providesAccountManager(context: Context): AccountManager {
        return AccountManager.get(context)
    }

    @JvmStatic
    @Provides
    @ForAccountType(AccountType.OPENCACHING_PL)
    fun providesAccountPL(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_PL)
    }

    @JvmStatic
    @Provides
    @ForAccountType(AccountType.OPENCACHING_DE)
    fun providesAccountDE(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_DE)
    }

    @JvmStatic
    @Provides
    @ForAccountType(AccountType.OPENCACHING_US)
    fun providesAccountUS(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_US)
    }

    @JvmStatic
    @Provides
    @ForAccountType(AccountType.OPENCACHING_NL)
    fun providesAccountNL(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_NL)
    }

    @JvmStatic
    @Provides
    @ForAccountType(AccountType.OPENCACHING_RO)
    fun providesAccountRO(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_RO)
    }

    @JvmStatic
    @Provides
    @ForAccountType(AccountType.OPENCACHING_UK)
    fun providesAccountUK(accountManager: AccountManager): Account {
        return accountManager.getAccount(AccountType.OPENCACHING_UK)
    }
}