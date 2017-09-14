package com.arcao.opencaching4locus.model

data class Image(
        val uuid : String,
        val url : String,
        val thumb_url : String,
        val caption : String? = null,
        val is_spoiler : Boolean = false
)