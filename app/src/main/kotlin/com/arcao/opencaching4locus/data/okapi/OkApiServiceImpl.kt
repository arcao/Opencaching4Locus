package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.account.Account
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.data.retrofit.interceptor.AuthenticationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class OkApiServiceImpl(private val serviceType: OkApiServiceType, private val account: Account, private val okHttpClient: OkHttpClient, private val retrofitBuilder: Retrofit.Builder) : OkApiService {
    private val apiInstance : OkApi by lazy {
        retrofitBuilder
                .client(okHttpClient
                        .newBuilder()
                        .addInterceptor(AuthenticationInterceptor(account))
                        .build()
                )
                .baseUrl(serviceType.endpoint)
                .build()
                .create(OkApi::class.java)
    }

    override fun getAccountType(): AccountType = account.accountType
    override fun getServiceType(): OkApiServiceType = serviceType
    override fun getApi(): OkApi = apiInstance
    override fun isAuthenticated(): Boolean  = account.isAuthenticated()
}

