package com.arcao.opencaching4locus.data.network

import retrofit2.Call
import retrofit2.http.GET

interface OkApi {
    @GET("caches/geocaches")
    fun geocache(): Call<Any>
}
