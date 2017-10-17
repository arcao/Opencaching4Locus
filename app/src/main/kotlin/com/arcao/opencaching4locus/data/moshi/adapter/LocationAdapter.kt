package com.arcao.opencaching4locus.data.moshi.adapter

import com.arcao.opencaching4locus.data.moshi.annotation.StringJson
import com.arcao.opencaching4locus.model.Location
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class LocationAdapter {
    @FromJson
    @StringJson
    fun fromJson(location: String): Location {
        val parts = location.split("|")
        return Location(parts[0].toDouble(), parts[1].toDouble())
    }

    @ToJson
    fun toJson(@StringJson location: Location): String = "${location.latitude}|${location.longitude}"
}