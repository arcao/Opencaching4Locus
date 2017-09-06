package com.arcao.opencaching4locus.model

import java.util.*

data class Log(
    val uuid : String,
    val cache_code : String,
    val date: Date,
    val user : User,
    val type : String,
    val comment : String,
    val images : List<Image>
) {
    companion object {
        val FIELD_UUID = "uuid"
        val FIELD_CACHE_CODE = "cache_code"
        val FIELD_DATE = "date"
        val FIELD_USER = "user"
        val FIELD_TYPE = "type"
        val FIELD_COMMENT = "comment"
        val FIELD_IMAGES = "images"

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