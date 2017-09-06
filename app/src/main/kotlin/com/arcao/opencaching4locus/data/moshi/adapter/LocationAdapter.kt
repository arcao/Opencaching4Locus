package com.arcao.opencaching4locus.data.moshi.adapter

import com.arcao.opencaching4locus.model.Location
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class LocationAdapter {
    @ToJson fun toJson(location : Location) : String {
        return "${location.latitude}|${location.longitude}"
    }

    @FromJson fun fromJson(location: String) : Location {
        val parts = location.split("|")
        return Location(parts[0].toDouble(), parts[1].toDouble())
    }
}