package com.arcao.opencaching4locus.model.response

import com.arcao.opencaching4locus.data.moshi.annotation.StringJson
import com.arcao.opencaching4locus.model.Location
import com.arcao.opencaching4locus.model.enums.WaypointType
import com.squareup.moshi.Json

data class Waypoint(
        @Json(name = "name") val name: String,
        @StringJson @Json(name = "location") val location: Location,
        @Json(name = "type") val type: WaypointType,
        @Json(name = "type_name") val typeName: String,
        @Json(name = "description") val description: String? = null
)