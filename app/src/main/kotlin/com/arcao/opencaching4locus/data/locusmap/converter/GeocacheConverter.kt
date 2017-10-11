package com.arcao.opencaching4locus.data.locusmap.converter

import com.arcao.opencaching4locus.model.enums.GeocacheStatus
import com.arcao.opencaching4locus.model.response.Geocache
import locus.api.objects.extra.Location
import locus.api.objects.extra.Waypoint
import locus.api.objects.geocaching.GeocachingData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocacheConverter @Inject constructor(
        private val geocacheTypeConverter: LocusMapGeocacheTypeConverter,
        private val containerConverter: LocusMapContainerConverter,
        private val imageConverter: LocusMapImageConverter,
        private val attributeConverter: LocusMapAttributeConverter,
        private val waypointConverter: LocusMapWaypointConverter,
        private val trackableConverter: LocusMapTrackableConverter,
        private val logConverter: LocusMapLogConverter

) {
    fun convert(geocache: Geocache): Waypoint {

        val geocachingData = GeocachingData().apply {
            cacheID = geocache.code
            name = geocache.name
            type = geocacheTypeConverter.convert(geocache.type)
            difficulty = geocache.difficulty
            terrain = geocache.terrain
            owner = geocache.owner?.username
            placedBy = geocache.owner?.username
            isAvailable = geocache.status == GeocacheStatus.AVAILABLE
            isArchived = geocache.status == GeocacheStatus.ARCHIVED
            isPremiumOnly = false
            cacheUrl = geocache.url
            dateHidden = geocache.dateHidden?.time ?: 0
            datePublished = geocache.dateCreated?.time ?: 0
            container = containerConverter.convert(geocache.size)
            isFound = geocache.isFound
            country = geocache.country
            state = geocache.state
            setDescriptions(geocache.shortDescription, true, geocache.description, true)
            encodedHints = geocache.hint
            notes = geocache.myNotes
            favoritePoints = geocache.recommendations

            geocache.images.forEach {
                addImage(imageConverter.convert(it))
            }

            geocache.attributeACodes.forEach {
                attributeConverter.convert(it)?.let {
                    attributes.add(it)
                }
            }

            geocache.altWaypoints.forEach {
                waypoints.add(waypointConverter.convert(it))
            }

            geocache.trackables.forEach {
                trackables.add(trackableConverter.convert(it))
            }

            geocache.latestLogs.forEach {
                logs.add(logConverter.convert(it))
            }
        }

        val location = Location(geocache.code).apply {
            latitude = geocache.location.latitude
            longitude = geocache.location.longitude
        }

        return Waypoint(geocache.name, location).apply {
            gcData = geocachingData
        }
    }
}

