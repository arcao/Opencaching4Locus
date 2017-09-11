package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.retrofit.converter.ArraySeparator
import com.arcao.opencaching4locus.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OkApi {
    @GET("services/caches/geocaches")
    fun geocaches(
            @ArraySeparator @Query("cacheCodes") cacheCodes: Array<String>,
            @ArraySeparator @Query("fields") fields: Array<String> = Geocache.FORMAT_LIVEMAP,
            @Query("lpc") logsPerCache : Int = 5,
            @ArraySeparator @Query("log_fields") logFields : Array<String> = Log.FORMAT_LIVEMAP
    ): Call<Map<String, Geocache>>

    @GET("services/caches/geocache")
    fun geocache(
            @Query("cacheCode") cacheCode: String,
            @ArraySeparator @Query("fields") fields: Array<String> = Geocache.FORMAT_LIVEMAP,
            @Query("lpc") logsPerCache : Int = 5,
            @ArraySeparator @Query("log_fields") logFields : Array<String> = Log.FORMAT_LIVEMAP
    ) : Call<Geocache>

    @GET("services/caches/search/nearest")
    fun nearest(
            @Query("center") location : Location,
            @Query("limit") limit : Int = 100,
            @Query("offset") offset : Int = 0,
            @Query("type") type : Array<String>? = null,
            @Query("difficulty") difficulty : FloatRange? = null,
            @Query("terrain") terrain : FloatRange? = null,
            @ArraySeparator @Query("size2") size : Array<String>? = null,
            @Query("found_status") foundStatus : String = "either",
            @Query("ignored_status") ignoredStatus : String = "either",
            @Query("exclude_my_own") excludeMyOwn : Boolean = false
    ) : Call<GeocacheSearchResult>

    @GET("services/caches/search/bbox")
    fun bbox(
            @Query("bbox") bbox : BoundingBox,
            @Query("limit") limit : Int = 100,
            @Query("offset") offset : Int = 0,
            @Query("type") type : Array<String>? = null,
            @Query("difficulty") difficulty : FloatRange? = null,
            @Query("terrain") terrain : FloatRange? = null,
            @ArraySeparator @Query("size2") size : Array<String>? = null,
            @Query("found_status") foundStatus : String = "either",
            @Query("ignored_status") ignoredStatus : String = "either",
            @Query("exclude_my_own") excludeMyOwn : Boolean = false
    ) : Call<GeocacheSearchResult>
}
