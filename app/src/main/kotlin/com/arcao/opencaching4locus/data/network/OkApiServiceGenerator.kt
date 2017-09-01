package com.arcao.opencaching4locus.data.network

import com.arcao.opencaching4locus.data.network.interceptor.AuthenticationInterceptor
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object OkApiServiceGenerator {
    val API_BASE_URL = "https://your.api-base.url"

    private val httpClient = OkHttpClient.Builder()

    private val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())

    private var retrofit = builder.build()

    fun <S> createService(serviceClass: Class<S>): S {
        return createService(serviceClass, null, null)
    }

    fun <S> createService(
            serviceClass: Class<S>, username: String?, password: String?): S {
        if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            val authToken = Credentials.basic(username!!, password!!)
            return createService(serviceClass, authToken)
        }

        return createService(serviceClass, null, null)
    }

    fun <S> createService(serviceClass: Class<S>, authToken: String?): S {
        if (!authToken.isNullOrEmpty()) {
            val interceptor = AuthenticationInterceptor(authToken)

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)

                builder.client(httpClient.build())
                retrofit = builder.build()
            }
        }

        return retrofit.create(serviceClass)
    }
}
