package com.arcao.opencaching4locus.data

import com.arcao.opencaching4locus.BuildConfig
import com.arcao.opencaching4locus.PerApp
import com.arcao.opencaching4locus.data.account.AccountModule
import com.arcao.opencaching4locus.data.moshi.adapter.DateAdapter
import com.arcao.opencaching4locus.data.moshi.adapter.LocationAdapter
import com.arcao.opencaching4locus.data.moshi.adapter.SafeEnumsAdapterFactory
import com.arcao.opencaching4locus.data.okapi.OkApiModule
import com.arcao.opencaching4locus.data.retrofit.adapter.RxJava2ErrorHandlingCallAdapterFactory
import com.arcao.opencaching4locus.data.retrofit.converter.ArraySeparatorConverterFactory
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(includes = [AccountModule::class, OkApiModule::class])
object DataModule {
    @JvmStatic
    @PerApp
    @Provides
    fun provideMoshi() : Moshi = Moshi.Builder().apply {
        add(KotlinJsonAdapterFactory())
        add(SafeEnumsAdapterFactory())
        add(LocationAdapter())
        add(DateAdapter())
    }.build()

    @JvmStatic
    @PerApp
    @Provides
    fun provideOkHttpClient() : OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
    }.build()

    @JvmStatic
    @PerApp
    @Provides
    fun provideRetrofitBuilder(moshi: Moshi) : Retrofit.Builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2ErrorHandlingCallAdapterFactory.create())
            .addConverterFactory(ArraySeparatorConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
}