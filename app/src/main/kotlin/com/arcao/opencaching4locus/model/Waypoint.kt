package com.arcao.opencaching4locus.model

data class Waypoint(
     val name : String,
     val location : Location,
     val type : String,
     val type_name : String,
     val description : String
)