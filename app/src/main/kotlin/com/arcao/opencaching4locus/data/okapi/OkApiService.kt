package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.model.BoundingBox
import com.arcao.opencaching4locus.model.FloatRange
import com.arcao.opencaching4locus.model.Geocache
import com.arcao.opencaching4locus.model.Location
import io.reactivex.Flowable
import io.reactivex.Maybe

interface OkApiService {
    fun accountType() : AccountType
    fun serviceType() : OkApiServiceType
    fun authenticated() : Boolean

    fun geocache(code : String, logsPerCache : Int): Maybe<Geocache>
    fun nearestGeocaches(
            location : Location,
            limit: Int = 100,
            type : Array<String>? = null,
            difficulty : FloatRange? = null,
            terrain : FloatRange? = null,
            size : Array<String>? = null,
            foundStatus : String = "either",
            ignoredStatus : String = "either",
            excludeMyOwn : Boolean = false,
            logsPerCache : Int = 5) : Flowable<Geocache>
    fun liveMapGeocaches(
            bbox : BoundingBox,
            limit: Int = 100,
            type : Array<String>? = null,
            difficulty : FloatRange? = null,
            terrain : FloatRange? = null,
            size : Array<String>? = null,
            foundStatus : String = "either",
            ignoredStatus : String = "either",
            excludeMyOwn : Boolean = false,
            logsPerCache : Int) : Flowable<Geocache>
}

