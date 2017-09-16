package com.arcao.opencaching4locus.model

import com.arcao.opencaching4locus.data.moshi.annotation.StringJson

data class Waypoint(
        val name: String,
        @StringJson val location: Location,
        val type: WaypointType,
        val type_name: String,
        val description: String? = null
)