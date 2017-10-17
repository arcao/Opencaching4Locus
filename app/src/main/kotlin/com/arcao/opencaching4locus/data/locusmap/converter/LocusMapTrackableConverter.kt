package com.arcao.opencaching4locus.data.locusmap.converter

import com.arcao.opencaching4locus.PerApp
import com.arcao.opencaching4locus.model.response.Trackable
import locus.api.objects.geocaching.GeocachingTrackable
import javax.inject.Inject

@PerApp
class LocusMapTrackableConverter @Inject constructor() {
    fun convert(trackable: Trackable): GeocachingTrackable = GeocachingTrackable().apply {
        name = trackable.name
        srcDetails = trackable.url
    }

}