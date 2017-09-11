package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.account.Account
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.data.account.ForAccountType
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
class OkApiModule {
    @Provides
    @IntoMap
    @ForOkApiService(OkApiServiceType.OPENCACHING_PL)
    fun provideOkApiServicesPL(@ForAccountType(AccountType.OPENCACHING_PL) account: Account, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(OkApiServiceType.OPENCACHING_PL, account, okHttpClient, retrofitBuilder)
    }

    @Provides
    @IntoMap
    @ForOkApiService(OkApiServiceType.OPENCACHING_DE)
    fun provideOkApiServicesDE(@ForAccountType(AccountType.OPENCACHING_DE) account: Account, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(OkApiServiceType.OPENCACHING_DE, account, okHttpClient, retrofitBuilder)
    }

    @Provides
    @IntoMap
    @ForOkApiService(OkApiServiceType.OPENCACHING_US)
    fun provideOkApiServicesUS(@ForAccountType(AccountType.OPENCACHING_US) account: Account, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(OkApiServiceType.OPENCACHING_US, account, okHttpClient, retrofitBuilder)
    }

    @Provides
    @IntoMap
    @ForOkApiService(OkApiServiceType.OPENCACHING_NL)
    fun provideOkApiServicesNL(@ForAccountType(AccountType.OPENCACHING_NL) account: Account, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(OkApiServiceType.OPENCACHING_NL, account, okHttpClient, retrofitBuilder)
    }

    @Provides
    @IntoMap
    @ForOkApiService(OkApiServiceType.OPENCACHING_RO)
    fun provideOkApiServicesRO(@ForAccountType(AccountType.OPENCACHING_RO) account: Account, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(OkApiServiceType.OPENCACHING_RO, account, okHttpClient, retrofitBuilder)
    }

    @Provides
    @IntoMap
    @ForOkApiService(OkApiServiceType.OPENCACHING_UK)
    fun provideOkApiServicesUK(@ForAccountType(AccountType.OPENCACHING_UK) account: Account, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(OkApiServiceType.OPENCACHING_UK, account, okHttpClient, retrofitBuilder)
    }
}