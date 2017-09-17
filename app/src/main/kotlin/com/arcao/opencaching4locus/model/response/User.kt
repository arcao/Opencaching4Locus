package com.arcao.opencaching4locus.model.response

import com.arcao.opencaching4locus.data.moshi.annotation.StringJson
import com.arcao.opencaching4locus.model.Location
import com.squareup.moshi.Json
import java.util.*

data class User(
        @Json(name = "uuid") val uuid: String,
        @Json(name = "username") val username: String,
        @Json(name = "profile_url") val profileUrl: String,
        @Json(name = "date_registered") val dateRegistered: Date? = null,
        @Json(name = "caches_found") val cachesFound: Int = 0,
        @Json(name = "caches_notfound") val cachesNotFound: Int = 0,
        @Json(name = "caches_hidden") val cachesHidden: Int = 0,
        @Json(name = "rcmds_given") val recommendsGiven: Int = 0,
        @StringJson @Json(name = "home_location") val homeLocation: Location? = null

) {
    companion object {
        private const val FIELD_UUID = "uuid"
        private const val FIELD_USERNAME = "username"
        private const val FIELD_PROFILE_URL = "profile_url"
        private const val FIELD_DATE_REGISTERED = "date_registered"
        private const val FIELD_CACHES_FOUND = "caches_found"
        private const val FIELD_CACHES_NOTFOUND = "caches_notfound"
        private const val FIELD_CACHES_HIDDEN = "caches_hidden"
        private const val FIELD_RCMDS_GIVEN = "rcmds_given"
        private const val FIELD_HOME_LOCATION = "home_location"

        val FORMAT_SIGNED = arrayOf(
                FIELD_UUID,
                FIELD_USERNAME,
                FIELD_PROFILE_URL,
                FIELD_DATE_REGISTERED,
                FIELD_CACHES_FOUND,
                FIELD_CACHES_NOTFOUND,
                FIELD_CACHES_HIDDEN,
                FIELD_RCMDS_GIVEN,
                FIELD_HOME_LOCATION
        )

        val FORMAT_UNSIGNED = arrayOf(
                FIELD_UUID,
                FIELD_USERNAME,
                FIELD_PROFILE_URL,
                FIELD_DATE_REGISTERED,
                FIELD_CACHES_FOUND,
                FIELD_CACHES_NOTFOUND,
                FIELD_CACHES_HIDDEN,
                FIELD_RCMDS_GIVEN
        )
    }
}