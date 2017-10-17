package com.arcao.opencaching4locus.model.request

import com.arcao.opencaching4locus.model.Location

data class BoundingBox(
        val topLeft: Location,
        val bottomRight: Location
) {
    constructor(minLatitude: Double, minLongitude: Double, maxLatitude: Double, maxLongitude: Double)
            : this(Location(maxLatitude, minLongitude), Location(minLatitude, maxLongitude))

    override fun toString(): String =// boundary box format: S|W|N|E
            arrayOf(
                    bottomRight.latitude,
                    topLeft.longitude,
                    topLeft.latitude,
                    bottomRight.longitude
            ).joinToString("|")

    companion object {
        fun from(string: String) : BoundingBox {
            if (string.count { it == '|' } != 3)
                throw IllegalArgumentException("Wrong format.")

            val coordinates = string.split("|").map { it.toDouble() }
            return BoundingBox(coordinates[0], coordinates[1], coordinates[2], coordinates[3])
        }
    }
}