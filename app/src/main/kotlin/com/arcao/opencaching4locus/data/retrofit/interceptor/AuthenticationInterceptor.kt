package com.arcao.opencaching4locus.data.retrofit.interceptor

import com.arcao.opencaching4locus.data.account.Account
import com.arcao.opencaching4locus.data.okapi.OkApiServiceType
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(private val account: Account, private val serviceType: OkApiServiceType) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        return if (account.isAuthenticated()) {
            chain.proceed(original)
        } else {
            val httpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("consumer_key", serviceType.consumerKey)
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(httpUrl)

            chain.proceed(requestBuilder.build())
        }
    }
}