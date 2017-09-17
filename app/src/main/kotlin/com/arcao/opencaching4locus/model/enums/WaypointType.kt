package com.arcao.opencaching4locus.model.enums

enum class WaypointType(val waypointName: String) {
    PARKING("parking"),
    PATH("path"),
    STAGE("stage"),
    PHYSICAL_STAGE("physical-stage"),
    VIRTUAL_STAGE("virtual-stage"),
    FINAL("final"),
    POI("poi"),
    OTHER("other"),
    USER_COORDS("user-coords");

    override fun toString(): String = waypointName

    companion object {
        fun from(waypointName: String?) = values().find { it.waypointName == waypointName } ?: OTHER
    }

}