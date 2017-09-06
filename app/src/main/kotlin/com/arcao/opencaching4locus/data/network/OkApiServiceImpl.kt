package com.arcao.opencaching4locus.data.network

import android.content.Context
import com.arcao.opencaching4locus.data.network.interceptor.AuthenticationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class OkApiServiceImpl(private val context: Context, private val okHttpClient: OkHttpClient, private val retrofitBuilder: Retrofit.Builder, private val serviceType: OkApiServiceType) : OkApiService {
    private val apiInstance : OkApi by lazy {
        retrofitBuilder
                .client(okHttpClient.newBuilder().addInterceptor(AuthenticationInterceptor(context, serviceType)).build())
                .baseUrl(serviceType.serviceUrl)
                .build()
                .create(OkApi::class.java)
    }

    override fun getServiceType(): OkApiServiceType {
        return serviceType
    }

    override fun getApi(): OkApi {
        return apiInstance
    }

    override fun authorized(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}