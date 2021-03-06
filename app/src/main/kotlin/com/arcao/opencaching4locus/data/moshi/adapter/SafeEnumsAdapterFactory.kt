package com.arcao.opencaching4locus.data.moshi.adapter

import com.arcao.opencaching4locus.model.enums.GeocacheSize
import com.arcao.opencaching4locus.model.enums.GeocacheStatus
import com.arcao.opencaching4locus.model.enums.LogType
import com.arcao.opencaching4locus.model.enums.WaypointType
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class SafeEnumsAdapterFactory : JsonAdapter.Factory {
    override fun create(type: Type?, annotations: MutableSet<out Annotation>?, moshi: Moshi?): JsonAdapter<*>? = when (type) {
        GeocacheSize::class.java -> GeocacheSizeAdapter.INSTANCE
        GeocacheStatus::class.java -> GeocacheStatusAdapter.INSTANCE
        WaypointType::class.java -> WaypointTypeAdapter.INSTANCE
        LogType::class.java -> LogTypeAdapter.INSTANCE
        else -> null
    }

    abstract class SafeEnumAdapter<T> : JsonAdapter<T>() {
        abstract fun fromString(value: String?): T
        override fun fromJson(reader: JsonReader?): T? = fromString(reader?.nextString())
        override fun toJson(writer: JsonWriter?, value: T?) {
            writer?.value(value.toString())
        }
    }

    class GeocacheSizeAdapter : SafeEnumAdapter<GeocacheSize>() {
        companion object {
            val INSTANCE = GeocacheSizeAdapter()
        }

        override fun fromString(value: String?): GeocacheSize = GeocacheSize.from(value)
    }

    class GeocacheStatusAdapter : SafeEnumAdapter<GeocacheStatus>() {
        companion object {
            val INSTANCE = GeocacheStatusAdapter()
        }

        override fun fromString(value: String?): GeocacheStatus = GeocacheStatus.from(value)
    }

    class LogTypeAdapter : SafeEnumAdapter<LogType>() {
        companion object {
            val INSTANCE = LogTypeAdapter()
        }

        override fun fromString(value: String?): LogType = LogType.from(value)
    }

    class WaypointTypeAdapter : SafeEnumAdapter<WaypointType>() {
        companion object {
            val INSTANCE = WaypointTypeAdapter()
        }

        override fun fromString(value: String?): WaypointType = WaypointType.from(value)
    }
}