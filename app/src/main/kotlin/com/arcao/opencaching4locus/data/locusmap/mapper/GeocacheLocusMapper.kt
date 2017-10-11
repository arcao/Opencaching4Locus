package com.arcao.opencaching4locus.data.locusmap.mapper

import com.arcao.opencaching4locus.data.locusmap.converter.GeocacheConverter
import com.arcao.opencaching4locus.model.response.Geocache
import locus.api.objects.extra.Waypoint
import javax.inject.Inject
import javax.inject.Singleton


@Suppress("NOTHING_TO_INLINE")
@Singleton
class GeocacheLocusMapper @Inject constructor(
    private val geocacheConverter: GeocacheConverter
) {

    inline fun toLiveMapWaypoint(geocache: Geocache): Waypoint = toWaypoint(geocache)

    fun toWaypoint(geocache: Geocache): Waypoint = geocacheConverter.convert(geocache)
}