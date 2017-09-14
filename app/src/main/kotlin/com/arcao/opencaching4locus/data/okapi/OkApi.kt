package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.retrofit.converter.ArraySeparator
import com.arcao.opencaching4locus.model.*
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Query

interface OkApi {
    @GET("services/caches/geocaches")
    fun geocaches(
            @ArraySeparator @Query("cache_codes") cacheCodes: String,
            @ArraySeparator @Query("fields") fields: String = Geocache.FORMAT_LIVEMAP.joinToString("|"),
            @Query("lpc") logsPerCache : Int = 5,
            @ArraySeparator @Query("log_fields") logFields : String = Log.FORMAT_LIVEMAP.joinToString("|")
    ): Maybe<Map<String, Geocache>>

    @GET("services/caches/geocache")
    fun geocache(
            @Query("cache_code") cacheCode: String,
            @ArraySeparator @Query("fields") fields: String = Geocache.FORMAT_LIVEMAP.joinToString("|"),
            @Query("lpc") logsPerCache : Int = 5,
            @ArraySeparator @Query("log_fields") logFields : String = Log.FORMAT_LIVEMAP.joinToString("|")
    ) : Maybe<Geocache>

    @GET("services/caches/search/nearest")
    fun nearest(
            @Query("center") location : Location,
            @Query("limit") limit : Int = 100,
            @Query("offset") offset : Int = 0,
            @Query("type") type : Array<String>? = null,
            @Query("difficulty") difficulty : FloatRange? = null,
            @Query("terrain") terrain : FloatRange? = null,
            @ArraySeparator @Query("size2") size : String? = null,
            @Query("found_status") foundStatus : String? = null,
            @Query("ignored_status") ignoredStatus : String? = null,
            @Query("exclude_my_own") excludeMyOwn : Boolean? = null
    ) : Maybe<GeocacheSearchResponse>

    @GET("services/caches/search/bbox")
    fun bbox(
            @Query("bbox") bbox : BoundingBox,
            @Query("limit") limit : Int = 100,
            @Query("offset") offset : Int = 0,
            @Query("type") type : Array<String>? = null,
            @Query("difficulty") difficulty : FloatRange? = null,
            @Query("terrain") terrain : FloatRange? = null,
            @ArraySeparator @Query("size2") size : String? = null,
            @Query("found_status") foundStatus : String? = null,
            @Query("ignored_status") ignoredStatus : String? = null,
            @Query("exclude_my_own") excludeMyOwn : Boolean? = null
    ) : Maybe<GeocacheSearchResponse>
}
