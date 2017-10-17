package com.arcao.opencaching4locus.data.locusmap.converter

import com.arcao.opencaching4locus.PerApp
import com.arcao.opencaching4locus.model.enums.WaypointType
import com.arcao.opencaching4locus.model.response.Waypoint
import locus.api.objects.geocaching.GeocachingWaypoint
import javax.inject.Inject

@PerApp
class LocusMapWaypointConverter @Inject constructor() {
    fun convert(waypoint: Waypoint): GeocachingWaypoint = GeocachingWaypoint().apply {
        name = waypoint.name
        type = convertType(waypoint.type)
        lat = waypoint.location.latitude
        lon = waypoint.location.longitude
        desc = waypoint.description
    }

    private fun convertType(type: WaypointType): String = when(type) {
        WaypointType.FINAL -> GeocachingWaypoint.CACHE_WAYPOINT_TYPE_FINAL
        WaypointType.OTHER -> GeocachingWaypoint.CACHE_WAYPOINT_TYPE_REFERENCE
        WaypointType.PARKING -> GeocachingWaypoint.CACHE_WAYPOINT_TYPE_PARKING
        WaypointType.PATH -> GeocachingWaypoint.CACHE_WAYPOINT_TYPE_TRAILHEAD
        WaypointType.PHYSICAL_STAGE -> GeocachingWaypoint.CACHE_WAYPOINT_TYPE_PHYSICAL_STAGE
        WaypointType.POI -> GeocachingWaypoint.CACHE_WAYPOINT_TYPE_REFERENCE
        WaypointType.STAGE -> GeocachingWaypoint.CACHE_WAYPOINT_TYPE_PHYSICAL_STAGE
        WaypointType.USER_COORDS -> GeocachingWaypoint.CACHE_WAYPOINT_TYPE_REFERENCE
        WaypointType.VIRTUAL_STAGE -> GeocachingWaypoint.CACHE_WAYPOINT_TYPE_VIRTUAL_STAGE
    }
}