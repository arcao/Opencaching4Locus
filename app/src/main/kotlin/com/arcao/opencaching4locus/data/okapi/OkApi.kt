package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.model.Location
import com.arcao.opencaching4locus.model.request.BoundingBox
import com.arcao.opencaching4locus.model.request.FloatRange
import com.arcao.opencaching4locus.model.response.Geocache
import com.arcao.opencaching4locus.model.response.GeocacheSearchResponse
import com.arcao.opencaching4locus.model.response.Log
import com.arcao.opencaching4locus.model.response.User
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

interface OkApi {
    @POST("services/caches/geocaches")
    fun geocaches(
            @Query("cache_codes") cacheCodes: String,
            @Query("fields") fields: String = Geocache.FORMAT_LIVEMAP.joinToString("|"),
            @Query("lpc") logsPerCache: Int = 5,
            @Query("log_fields") logFields: String = Log.FORMAT_LIVEMAP.joinToString("|")
    ): Single<Map<String, Geocache>>

    @POST("services/caches/geocache")
    fun geocache(
            @Query("cache_code") cacheCode: String,
            @Query("fields") fields: String = Geocache.FORMAT_LIVEMAP.joinToString("|"),
            @Query("lpc") logsPerCache: Int = 5,
            @Query("log_fields") logFields: String = Log.FORMAT_LIVEMAP.joinToString("|")
    ): Single<Geocache>

    @POST("services/caches/search/nearest")
    fun nearest(
            @Query("center") location: Location,
            @Query("limit") limit: Int = 100,
            @Query("offset") offset: Int = 0,
            @Query("type") type: String? = null,
            @Query("difficulty") difficulty: FloatRange? = null,
            @Query("terrain") terrain: FloatRange? = null,
            @Query("size2") size: String? = null,
            @Query("found_status") foundStatus: String? = null,
            @Query("ignored_status") ignoredStatus: String? = null,
            @Query("exclude_my_own") excludeMyOwn: Boolean? = null
    ): Maybe<GeocacheSearchResponse>

    @POST("services/caches/search/bbox")
    fun bbox(
            @Query("bbox") bbox: BoundingBox,
            @Query("limit") limit: Int = 100,
            @Query("offset") offset: Int = 0,
            @Query("type") type: String? = null,
            @Query("difficulty") difficulty: FloatRange? = null,
            @Query("terrain") terrain: FloatRange? = null,
            @Query("size2") size: String? = null,
            @Query("found_status") foundStatus: String? = null,
            @Query("ignored_status") ignoredStatus: String? = null,
            @Query("exclude_my_own") excludeMyOwn: Boolean? = null
    ): Single<GeocacheSearchResponse>

    @POST("services/users/user")
    fun user(
            @Query("user_uuid") userUuid: String? = null,
            @Query("fields") fields: String = User.FORMAT_UNSIGNED.joinToString("|")
    ): Single<User>
}
