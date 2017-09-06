package com.arcao.opencaching4locus.data.network.interceptor

import android.content.Context
import com.arcao.opencaching4locus.data.network.OkApiServiceType
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(private val context: Context, serviceType: OkApiServiceType) : Interceptor {
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