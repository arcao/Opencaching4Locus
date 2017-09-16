package com.arcao.opencaching4locus.model

data class Location(
        val latitude: Double,
        val longitude: Double
) {
    override fun toString(): String {
        return "$latitude|$longitude"
    }
}