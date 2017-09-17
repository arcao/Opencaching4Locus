package com.arcao.opencaching4locus.model.response

import com.arcao.opencaching4locus.model.enums.LogType
import com.squareup.moshi.Json
import java.util.*

data class Log(
        @Json(name = "uuid") val uuid: String? = null,
        @Json(name = "cache_code") val cache_code: String? = null,
        @Json(name = "date") val date: Date? = null,
        @Json(name = "user") val user: User? = null,
        @Json(name = "type") val type: LogType,
        @Json(name = "comment") val comment: String? = null,
        @Json(name = "images") val images: List<Image> = emptyList()
) {
    companion object {
        private const val FIELD_UUID = "uuid"
        private const val FIELD_CACHE_CODE = "cache_code"
        private const val FIELD_DATE = "date"
        private const val FIELD_USER = "user"
        private const val FIELD_TYPE = "type"
        private const val FIELD_COMMENT = "comment"
        private const val FIELD_IMAGES = "images"

        val FORMAT_FULL = arrayOf(
                FIELD_UUID,
                FIELD_CACHE_CODE,
                FIELD_DATE,
                FIELD_USER,
                FIELD_TYPE,
                FIELD_COMMENT,
                FIELD_IMAGES
        )

        val FORMAT_LIVEMAP = arrayOf(
                FIELD_TYPE
        )
    }
}