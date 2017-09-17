package com.arcao.opencaching4locus.model.response

import com.squareup.moshi.Json

data class Image(
        @Json(name = "uuid") val uuid: String,
        @Json(name = "url") val url: String,
        @Json(name = "thumb_url") val thumbnailUrl: String,
        @Json(name = "caption") val caption: String? = null,
        @Json(name = "is_spoiler") val isSpoiler: Boolean = false
)