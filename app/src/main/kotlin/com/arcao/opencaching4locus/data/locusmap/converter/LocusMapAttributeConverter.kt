package com.arcao.opencaching4locus.data.locusmap.converter

import locus.api.objects.geocaching.GeocachingAttribute
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocusMapAttributeConverter @Inject constructor() {
    private val cache = HashMap<String, GeocachingAttribute>()

    fun convert(aCode: String): GeocachingAttribute? {
        if (cache[aCode] != null) {
            return cache[aCode]
        }

        val attribute = when (aCode) {
        // Garmin's wireless beacon
            "A9" -> GeocachingAttribute(60, true)

        // Field Puzzle / Mystery
            "A15" -> GeocachingAttribute(47, true)

        // Wheelchair accessible
            "A18" -> GeocachingAttribute(24, true)

        // Near the parking area
            "A19" -> GeocachingAttribute(53, true)

        // Long walk
            "A21" -> GeocachingAttribute(9, true)

        // Swamp, marsh or wading
            "A22" -> GeocachingAttribute(11, true)

        // Some climbing (no gear needed)
            "A24" -> GeocachingAttribute(10, true)

        // Swimming required
            "A25" -> GeocachingAttribute(12, true)

        // Access or parking fee
            "A26" -> GeocachingAttribute(2, true)

        // Bikes allowed
            "A27" -> GeocachingAttribute(32, true)

        // Parking area nearby
            "A33" -> GeocachingAttribute(25, true)

        // Public transportation
            "A34" -> GeocachingAttribute(26, true)

        // Drinking water nearby
            "A35" -> GeocachingAttribute(27, true)

        // Public restrooms nearby
            "A36" -> GeocachingAttribute(28, true)

        // Public phone nearby
            "A37" -> GeocachingAttribute(29, true)

        // Available 24/7
            "A39" -> GeocachingAttribute(13, true)

        // Not available 24/7
            "A40" -> GeocachingAttribute(13, false)

        // Not recommended at night
            "A41" -> GeocachingAttribute(14, false)

        // Recommended at night
            "A42" -> GeocachingAttribute(14, true)

        // Only at night
            "A43" -> GeocachingAttribute(52, true)

        // All seasons
            "A44" -> GeocachingAttribute(62, false)

        // Only available during specified seasons
            "A45" -> GeocachingAttribute(62, true)

        // Available during winter
            "A47" -> GeocachingAttribute(15, true)

        // Flashlight required
            "A52" -> GeocachingAttribute(44, true)

        // Climbing gear required
            "A53" -> GeocachingAttribute(3, true)

        // Diving equipment required
            "A55" -> GeocachingAttribute(5, true)

        // Special tool required
            "A56" -> GeocachingAttribute(51, true)

        // Boat required
            "A57" -> GeocachingAttribute(4, true)

        // Dangerous area
            "A59" -> GeocachingAttribute(23, true)

        // Cliff / Rocks
            "A61" -> GeocachingAttribute(21, true)

        // Hunting grounds
            "A62" -> GeocachingAttribute(22, true)

        // Look out for thorns
            "A63" -> GeocachingAttribute(39, true)

        // Look out for ticks
            "A64" -> GeocachingAttribute(19, true)

        // Abandoned mines
            "A65" -> GeocachingAttribute(20, true)

        // Poisonous plants
            "A66" -> GeocachingAttribute(17, true)

        // Dangerous animals
            "A67" -> GeocachingAttribute(18, true)

        // Bring your children
            "A70" -> GeocachingAttribute(6, true)

        // Suitable for children
            "A71" -> GeocachingAttribute(6, true)

        // Available at specified hours (may require access fee)
            "A73" -> GeocachingAttribute(13, false)

        // Stealth required
            "A74" -> GeocachingAttribute(40, true)

            else -> null
        }

        if (attribute != null) {
            cache[aCode] = attribute
        }

        return attribute
    }

}