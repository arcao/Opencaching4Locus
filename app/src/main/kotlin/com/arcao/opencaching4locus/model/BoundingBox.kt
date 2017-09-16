package com.arcao.opencaching4locus.model

data class BoundingBox(
        val topLeft: Location,
        val bottomRight: Location
) {
    constructor(minLatitude: Double, minLongitude: Double, maxLatitude: Double, maxLongitude: Double)
            : this(Location(maxLatitude, minLongitude), Location(minLatitude, maxLongitude))

    override fun toString(): String {
        // boundary box format: S|W|N|E
        return "${bottomRight.latitude}|${topLeft.longitude}|${topLeft.latitude}|${bottomRight.longitude}"
    }
}