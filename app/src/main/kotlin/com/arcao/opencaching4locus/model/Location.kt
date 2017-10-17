package com.arcao.opencaching4locus.model

import locus.api.objects.extra.Location as LocusLocation

data class Location(
        val latitude: Double,
        val longitude: Double
) {
    override fun toString(): String = "$latitude|$longitude"

    companion object {
        fun from(location: LocusLocation) = Location(location.latitude, location.longitude)
    }
}