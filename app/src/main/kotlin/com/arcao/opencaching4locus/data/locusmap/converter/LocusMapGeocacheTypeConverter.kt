package com.arcao.opencaching4locus.data.locusmap.converter

import locus.api.objects.geocaching.GeocachingData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocusMapGeocacheTypeConverter @Inject constructor() {
    fun convert(type: String): Int = when(type) {
        "Traditional" -> GeocachingData.CACHE_TYPE_TRADITIONAL
        "Multi" -> GeocachingData.CACHE_TYPE_MULTI
        "Quiz" -> GeocachingData.CACHE_TYPE_MYSTERY
        "Virtual" -> GeocachingData.CACHE_TYPE_VIRTUAL
        "Event" -> GeocachingData.CACHE_TYPE_EVENT

        // TODO add other types
        else -> GeocachingData.CACHE_TYPE_MYSTERY
    }
}