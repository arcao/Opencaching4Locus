package com.arcao.opencaching4locus.data

import com.arcao.opencaching4locus.data.account.AccountModule
import com.arcao.opencaching4locus.data.moshi.adapter.LocationAdapter
import com.arcao.opencaching4locus.data.okapi.OkApiModule
import com.arcao.opencaching4locus.data.retrofit.converter.ArraySeparatorConverterFactory
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(includes = arrayOf(AccountModule::class, OkApiModule::class))
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ArraySeparatorConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
    }
}