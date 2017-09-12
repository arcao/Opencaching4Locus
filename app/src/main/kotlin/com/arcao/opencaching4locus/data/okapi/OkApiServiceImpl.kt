package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.account.Account
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.data.retrofit.interceptor.AuthenticationInterceptor
import com.arcao.opencaching4locus.model.*
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class OkApiServiceImpl(private val serviceType: OkApiServiceType, private val account: Account, private val okHttpClient: OkHttpClient, private val retrofitBuilder: Retrofit.Builder) : OkApiService {
    companion object {
        val GEOCACHES_PER_REQUEST = 50
    }

    private val apiInstance: OkApi by lazy {
        retrofitBuilder
                .client(okHttpClient.newBuilder()
                        .addInterceptor(AuthenticationInterceptor(account))
                        .build())
                .baseUrl(serviceType.endpoint)
                .build()
                .create(OkApi::class.java)
    }

    override fun accountType(): AccountType = account.accountType
    override fun serviceType(): OkApiServiceType = serviceType
    override fun authenticated(): Boolean = account.isAuthenticated()

    override fun geocache(code: String, logsPerCache: Int): Maybe<Geocache> {
        return apiInstance.geocache(
                cacheCode = code,
                fields = Geocache.FORMAT_FULL,
                logFields = Log.FORMAT_FULL,
                logsPerCache = logsPerCache
        ).subscribeOn(Schedulers.io())
    }

    override fun nearestGeocaches(location: Location, limit: Int, type: Array<String>?, difficulty: FloatRange?, terrain: FloatRange?, size: Array<String>?, foundStatus: String, ignoredStatus: String, excludeMyOwn: Boolean, logsPerCache: Int): Flowable<Geocache> {
        return apiInstance.nearest(
                location = location,
                limit = limit,
                type = type,
                difficulty = difficulty,
                terrain = terrain,
                size = size,
                foundStatus = foundStatus,
                ignoredStatus = ignoredStatus,
                excludeMyOwn = excludeMyOwn
        ).subscribeOn(Schedulers.io())
                .toFlowable()
                .flatMap { (results) ->
                    Flowable.fromArray(*results)
                            .buffer(GEOCACHES_PER_REQUEST)
                            .flatMap { codes ->
                                Flowable.fromIterable(
                                        apiInstance.geocaches(
                                                codes.toTypedArray(),
                                                fields = Geocache.FORMAT_FULL,
                                                logsPerCache = logsPerCache,
                                                logFields = Log.FORMAT_FULL
                                        ).blockingGet().values
                                )
                            }
                }
    }

    override fun liveMapGeocaches(bbox: BoundingBox, limit: Int, type: Array<String>?, difficulty: FloatRange?, terrain: FloatRange?, size: Array<String>?, foundStatus: String, ignoredStatus: String, excludeMyOwn: Boolean, logsPerCache: Int): Flowable<Geocache> {
        return apiInstance.bbox(
                bbox = bbox,
                limit = limit,
                type = type,
                difficulty = difficulty,
                terrain = terrain,
                size = size,
                foundStatus = foundStatus,
                ignoredStatus = ignoredStatus,
                excludeMyOwn = excludeMyOwn
        ).subscribeOn(Schedulers.io())
                .toFlowable()
                .flatMap { (results) ->
                    Flowable.fromArray(*results)
                            .buffer(GEOCACHES_PER_REQUEST)
                            .flatMap { codes ->
                                Flowable.fromIterable(
                                        apiInstance.geocaches(
                                                codes.toTypedArray(),
                                                fields = Geocache.FORMAT_LIVEMAP,
                                                logsPerCache = logsPerCache,
                                                logFields = Log.FORMAT_LIVEMAP
                                        ).blockingGet().values
                                )
                            }
                }
    }

}

