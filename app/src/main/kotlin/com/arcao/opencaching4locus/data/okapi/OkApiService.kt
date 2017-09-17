package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.model.Location
import com.arcao.opencaching4locus.model.enums.GeocacheSize
import com.arcao.opencaching4locus.model.request.BoundingBox
import com.arcao.opencaching4locus.model.request.FloatRange
import com.arcao.opencaching4locus.model.response.Geocache
import com.arcao.opencaching4locus.model.response.User
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface OkApiService {
    fun accountType(): AccountType
    fun serviceType(): OkApiServiceType
    fun authenticated(): Boolean

    fun geocache(code: String, logsPerCache: Int): Single<Geocache>
    fun nearestGeocaches(
            location: Location,
            limit: Int = 100,
            type: Array<String>? = null,
            difficulty: FloatRange? = null,
            terrain: FloatRange? = null,
            size: EnumSet<GeocacheSize>? = null,
            foundStatus: String? = null,
            ignoredStatus: String? = null,
            excludeMyOwn: Boolean? = null,
            logsPerCache: Int = 5): Flowable<Geocache>

    fun liveMapGeocaches(
            bbox: BoundingBox,
            limit: Int = 100,
            type: Array<String>? = null,
            difficulty: FloatRange? = null,
            terrain: FloatRange? = null,
            size: EnumSet<GeocacheSize>? = null,
            foundStatus: String? = null,
            ignoredStatus: String? = null,
            excludeMyOwn: Boolean? = null,
            logsPerCache: Int = 5): Flowable<Geocache>

    fun user(uuid: String? = null): Single<User>
}

