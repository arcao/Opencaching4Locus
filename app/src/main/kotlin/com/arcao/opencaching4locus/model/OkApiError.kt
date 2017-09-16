package com.arcao.opencaching4locus.model

data class OkApiError(
        val developer_message: String,
        val reason_stack: List<String>,
        val status: Int,
        val more_info: String
)