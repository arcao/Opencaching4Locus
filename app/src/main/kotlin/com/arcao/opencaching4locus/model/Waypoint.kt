package com.arcao.opencaching4locus.model

import com.arcao.opencaching4locus.data.moshi.annotation.LocationField

data class Waypoint(
        val name : String,
        @LocationField val location : Location,
        val type : String,
        val type_name : String,
        val description : String? = null
)