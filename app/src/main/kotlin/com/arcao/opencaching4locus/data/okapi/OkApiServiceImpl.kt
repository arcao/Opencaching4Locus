package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.account.Account
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.data.retrofit.interceptor.AuthenticationInterceptor
import com.arcao.opencaching4locus.model.Location
import com.arcao.opencaching4locus.model.enums.GeocacheSize
import com.arcao.opencaching4locus.model.request.BoundingBox
import com.arcao.opencaching4locus.model.request.FloatRange
import com.arcao.opencaching4locus.model.response.Geocache
import com.arcao.opencaching4locus.model.response.Log
import com.arcao.opencaching4locus.model.response.User
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.*

class OkApiServiceImpl(private val serviceType: OkApiServiceType, private val account: Account, private val okHttpClient: OkHttpClient, private val retrofitBuilder: Retrofit.Builder) : OkApiService {
    companion object {
        val GEOCACHES_PER_REQUEST = 50
    }

    private val apiInstance: OkApi by lazy {
        retrofitBuilder
                .client(okHttpClient.newBuilder()
                        .apply {
                            interceptors().add(0, AuthenticationInterceptor(account, serviceType))
                        }
                        .build())
                .baseUrl(serviceType.endpoint)
                .build()
                .create(OkApi::class.java)
    }

    override fun accountType(): AccountType = account.accountType
    override fun serviceType(): OkApiServiceType = serviceType
    override fun authenticated(): Boolean = account.authenticated

    override fun geocache(code: String, logsPerCache: Int): Single<Geocache> {
        return apiInstance.geocache(
                cacheCode = code,
                fields = Geocache.FORMAT_FULL.joinToString("|"),
                logFields = Log.FORMAT_FULL.joinToString("|"),
                logsPerCache = logsPerCache
        ).observeOn(Schedulers.io())
    }

    override fun nearestGeocaches(location: Location, limit: Int, type: Array<String>?, difficulty: FloatRange?, terrain: FloatRange?, size: EnumSet<GeocacheSize>?, foundStatus: String?, ignoredStatus: String?, excludeMyOwn: Boolean?, logsPerCache: Int): Flowable<Geocache> {
        return apiInstance.nearest(
                location = location,
                limit = limit,
                type = type?.joinToString("|"),
                difficulty = difficulty,
                terrain = terrain,
                size = size?.joinToString("|"),
                foundStatus = foundStatus,
                ignoredStatus = ignoredStatus,
                excludeMyOwn = excludeMyOwn
        ).subscribeOn(Schedulers.io())
                .toFlowable()
                .flatMap { (results) ->
                    Flowable.fromIterable(results)
                            .buffer(GEOCACHES_PER_REQUEST)
                            .flatMap { codes ->
                                Flowable.fromIterable(
                                        apiInstance.geocaches(
                                                codes.joinToString("|"),
                                                fields = Geocache.FORMAT_FULL.joinToString("|"),
                                                logsPerCache = logsPerCache,
                                                logFields = Log.FORMAT_FULL.joinToString("|")
                                        ).blockingGet().values
                                )
                            }
                }
    }

    override fun liveMapGeocaches(bbox: BoundingBox, limit: Int, type: Array<String>?, difficulty: FloatRange?, terrain: FloatRange?, size: EnumSet<GeocacheSize>?, foundStatus: String?, ignoredStatus: String?, excludeMyOwn: Boolean?, logsPerCache: Int): Flowable<Geocache> {
        return apiInstance.bbox(
                bbox = bbox,
                limit = limit,
                type = type?.joinToString("|"),
                difficulty = difficulty,
                terrain = terrain,
                size = size?.joinToString("|"),
                foundStatus = foundStatus,
                ignoredStatus = ignoredStatus,
                excludeMyOwn = excludeMyOwn
        ).subscribeOn(Schedulers.io())
                .toFlowable()
                .flatMap { (results) ->
                    Flowable.fromIterable(results)
                            .buffer(GEOCACHES_PER_REQUEST)
                            .flatMap { codes ->
                                Flowable.fromIterable(
                                        apiInstance.geocaches(
                                                codes.joinToString("|"),
                                                fields = Geocache.FORMAT_FULL.joinToString("|"),
                                                logsPerCache = logsPerCache,
                                                logFields = Log.FORMAT_FULL.joinToString("|")
                                        ).blockingGet().values
                                )
                            }
                }
    }

    override fun user(uuid: String?): Single<User> {
        return apiInstance.user(
                userUuid = uuid,
                fields = (if (uuid.isNullOrEmpty()) User.FORMAT_UNSIGNED else User.FORMAT_SIGNED).joinToString("|")
        ).subscribeOn(Schedulers.io())
    }

}

