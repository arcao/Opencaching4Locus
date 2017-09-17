package com.arcao.opencaching4locus.model.response

import com.arcao.opencaching4locus.data.moshi.annotation.StringJson
import com.arcao.opencaching4locus.model.*
import com.arcao.opencaching4locus.model.enums.GeocacheSize
import com.arcao.opencaching4locus.model.enums.GeocacheStatus
import com.squareup.moshi.Json
import java.util.*

data class Geocache(
        @Json(name = "code") val code: String,
        @Json(name = "name") val name: String,
        @StringJson @Json(name = "location") val location: Location,
        @Json(name = "type") val type: String,
        @Json(name = "status") val status: GeocacheStatus,
        @Json(name = "needs_maintenance") val needsMaintenance: Boolean = false,
        @Json(name = "url") val url: String? = null,
        @Json(name = "owner") val owner: User? = null,
        @Json(name = "gc_code") val gcCode: String? = null,
        @Json(name = "is_found") val isFound: Boolean = false,
        @Json(name = "is_not_found") val isNotFound: Boolean = false,
        @Json(name = "is_watched") val isWatched: Boolean = false,
        @Json(name = "is_ignored") val isIgnored: Boolean = false,
        @Json(name = "founds") val founds: Int = 0,
        @Json(name = "notfounds") val notFounds: Int = 0,
        @Json(name = "willattends") val willAttends: Int = 0,
        @Json(name = "size2") val size: GeocacheSize? = null,
        @Json(name = "difficulty") val difficulty: Float = 0F,
        @Json(name = "terrain") val terrain: Float = 0F,
        @Json(name = "rating") val rating: Float? = null,
        @Json(name = "recommendations") val recommendations: Int = 0,
        @Json(name = "req_passwd") val requirePassword: Boolean = false,
        @Json(name = "short_description") val shortDescription: String? = null,
        @Json(name = "description") val description: String? = null,
        @Json(name = "hint2") val hint: String? = null,
        @Json(name = "images") val images: List<Image> = emptyList(),
        @Json(name = "attr_acodes") val attributeACodes: List<String> = emptyList(),
        @Json(name = "attrnames") val attributeNames: List<String> = emptyList(),
        @Json(name = "latest_logs") val latestLogs: List<Log> = emptyList(),
        @Json(name = "my_notes") val myNotes: String? = null,
        @Json(name = "trackables") val trackables: List<Trackable> = emptyList(),
        @Json(name = "alt_waypoints") val altWaypoints: List<Waypoint> = emptyList(),
        @Json(name = "country") val country: String? = null,
        @Json(name = "state") val state: String? = null,
        @Json(name = "last_found") val lastFound: Date? = null,
        @Json(name = "last_modified") val lastModified: Date? = null,
        @Json(name = "date_created") val dateCreated: Date? = null,
        @Json(name = "date_hidden") val dateHidden: Date? = null
) {
    companion object {
        private const val FIELD_CODE = "code"
        private const val FIELD_NAME = "name"
        private const val FIELD_LOCATION = "location"
        private const val FIELD_TYPE = "type"
        private const val FIELD_STATUS = "status"
        private const val FIELD_NEEDS_MAINTENANCE = "needs_maintenance"
        private const val FIELD_URL = "url"
        private const val FIELD_OWNER = "owner"
        private const val FIELD_GC_CODE = "gc_code"
        private const val FIELD_IS_FOUND = "is_found"
        private const val FIELD_IS_NOT_FOUND = "is_not_found"
        private const val FIELD_IS_WATCHED = "is_watched"
        private const val FIELD_IS_IGNORED = "is_ignored"
        private const val FIELD_FOUNDS = "founds"
        private const val FIELD_NOTFOUNDS = "notfounds"
        private const val FIELD_WILLATTENDS = "willattends"
        private const val FIELD_SIZE2 = "size2"
        private const val FIELD_DIFFICULTY = "difficulty"
        private const val FIELD_TERRAIN = "terrain"
        private const val FIELD_RATING = "rating"
        private const val FIELD_RECOMMENDATIONS = "recommendations"
        private const val FIELD_REQ_PASSWD = "req_passwd"
        private const val FIELD_SHORT_DESCRIPTION = "short_description"
        private const val FIELD_DESCRIPTION = "description"
        private const val FIELD_HINT2 = "hint2"
        private const val FIELD_IMAGES = "images"
        private const val FIELD_ATTR_ACODES = "attr_acodes"
        private const val FIELD_ATTRNAMES = "attrnames"
        private const val FIELD_LATEST_LOGS = "latest_logs"
        private const val FIELD_MY_NOTES = "my_notes"
        private const val FIELD_TRACKABLES = "trackables"
        private const val FIELD_ALT_WPTS = "alt_wpts"
        private const val FIELD_COUNTRY = "country"
        private const val FIELD_STATE = "state"
        private const val FIELD_LAST_FOUND = "last_found"
        private const val FIELD_LAST_MODIFIED = "last_modified"
        private const val FIELD_DATE_CREATED = "date_created"
        private const val FIELD_DATE_HIDDEN = "date_hidden"

        val FORMAT_FULL = arrayOf(
                FIELD_CODE,
                FIELD_NAME,
                FIELD_LOCATION,
                FIELD_TYPE,
                FIELD_STATUS,
                FIELD_NEEDS_MAINTENANCE,
                FIELD_URL,
                FIELD_OWNER,
                FIELD_GC_CODE,
                //FIELD_IS_FOUND,
                //FIELD_IS_NOT_FOUND,
                //FIELD_IS_WATCHED,
                //FIELD_IS_IGNORED,
                FIELD_FOUNDS,
                FIELD_NOTFOUNDS,
                FIELD_WILLATTENDS,
                FIELD_SIZE2,
                FIELD_DIFFICULTY,
                FIELD_TERRAIN,
                FIELD_RATING,
                FIELD_RECOMMENDATIONS,
                FIELD_REQ_PASSWD,
                FIELD_SHORT_DESCRIPTION,
                FIELD_DESCRIPTION,
                FIELD_HINT2,
                FIELD_IMAGES,
                FIELD_ATTR_ACODES,
                FIELD_ATTRNAMES,
                FIELD_LATEST_LOGS,
                //FIELD_MY_NOTES,
                FIELD_TRACKABLES,
                FIELD_ALT_WPTS,
                FIELD_COUNTRY,
                FIELD_STATE,
                FIELD_LAST_FOUND,
                FIELD_LAST_MODIFIED,
                FIELD_DATE_CREATED,
                FIELD_DATE_HIDDEN
        )

        val FORMAT_LIVEMAP = arrayOf(
                FIELD_CODE,
                FIELD_NAME,
                FIELD_LOCATION,
                FIELD_TYPE,
                FIELD_STATUS,
                FIELD_NEEDS_MAINTENANCE,
                FIELD_IS_FOUND,
                FIELD_SIZE2,
                FIELD_DIFFICULTY,
                FIELD_TERRAIN,
                FIELD_RATING,
                FIELD_HINT2
        )
    }
}