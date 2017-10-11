package com.arcao.opencaching4locus.data.locusmap.converter

import com.arcao.opencaching4locus.model.response.Trackable
import locus.api.objects.geocaching.GeocachingTrackable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocusMapTrackableConverter @Inject constructor() {
    fun convert(trackable: Trackable): GeocachingTrackable {
        return GeocachingTrackable().apply {
            name = trackable.name
            srcDetails = trackable.url
        }
    }

}