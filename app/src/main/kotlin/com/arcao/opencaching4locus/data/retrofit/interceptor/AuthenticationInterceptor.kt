package com.arcao.opencaching4locus.data.retrofit.interceptor

import com.arcao.opencaching4locus.data.account.Account
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(private val account: Account) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original
                .newBuilder()
                .header("Authorization", "")
                .build()

        return chain.proceed(request)
    }
}