package com.arcao.opencaching4locus.data

import android.content.Context
import com.arcao.opencaching4locus.data.moshi.adapter.LocationAdapter
import com.arcao.opencaching4locus.data.network.OkApiService
import com.arcao.opencaching4locus.data.network.OkApiServiceImpl
import com.arcao.opencaching4locus.data.network.OkApiServiceType
import com.arcao.opencaching4locus.data.network.OkApiServiceTypeKey
import com.arcao.opencaching4locus.data.network.converter.ArraySeparatorConverterFactory
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class DataModule {
    @Provides fun provideMoshi() : Moshi {
        return Moshi.Builder().apply {
            add(KotlinJsonAdapterFactory())
            add(LocationAdapter::class)
        }.build()
    }

    @Provides fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
                .build()
    }

    @Provides fun provideRetrofitBuilder(moshi: Moshi) : Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(ArraySeparatorConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Provides
    @IntoMap
    @OkApiServiceTypeKey(OkApiServiceType.OPENCACHING_PL)
    fun provideOkApiServicesPL(context : Context, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(context, okHttpClient, retrofitBuilder, OkApiServiceType.OPENCACHING_PL)
    }

    @Provides
    @IntoMap
    @OkApiServiceTypeKey(OkApiServiceType.OPENCACHING_DE)
    fun provideOkApiServicesDE(context : Context, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(context, okHttpClient, retrofitBuilder, OkApiServiceType.OPENCACHING_DE)
    }

    @Provides
    @IntoMap
    @OkApiServiceTypeKey(OkApiServiceType.OPENCACHING_US)
    fun provideOkApiServicesUS(context : Context, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(context, okHttpClient, retrofitBuilder, OkApiServiceType.OPENCACHING_US)
    }

    @Provides
    @IntoMap
    @OkApiServiceTypeKey(OkApiServiceType.OPENCACHING_NL)
    fun provideOkApiServicesNL(context : Context, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(context, okHttpClient, retrofitBuilder, OkApiServiceType.OPENCACHING_NL)
    }

    @Provides
    @IntoMap
    @OkApiServiceTypeKey(OkApiServiceType.OPENCACHING_RO)
    fun provideOkApiServicesRO(context : Context, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(context, okHttpClient, retrofitBuilder, OkApiServiceType.OPENCACHING_RO)
    }

    @Provides
    @IntoMap
    @OkApiServiceTypeKey(OkApiServiceType.OPENCACHING_UK)
    fun provideOkApiServicesUK(context : Context, okHttpClient: OkHttpClient, retrofitBuilder: Retrofit.Builder) : OkApiService {
        return OkApiServiceImpl(context, okHttpClient, retrofitBuilder, OkApiServiceType.OPENCACHING_UK)
    }

}