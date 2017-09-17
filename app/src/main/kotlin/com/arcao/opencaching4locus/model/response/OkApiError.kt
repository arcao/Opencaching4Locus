package com.arcao.opencaching4locus.model.response

import com.squareup.moshi.Json

data class OkApiError(
        @Json(name = "developer_message") val developerMessage: String,
        @Json(name = "reason_stack") val reasonStack: List<String>,
        @Json(name = "status") val status: Int,
        @Json(name = "more_info") val moreInfo: String
)