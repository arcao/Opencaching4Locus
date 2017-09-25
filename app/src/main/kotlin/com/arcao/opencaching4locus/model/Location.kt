package com.arcao.opencaching4locus.model

import locus.api.objects.extra.Location

data class Location(
        val latitude: Double,
        val longitude: Double
) {
    override fun toString(): String {
        return "$latitude|$longitude"
    }

    companion object {
        fun from(location: Location) = Location(location.latitude, location.longitude)
    }
}