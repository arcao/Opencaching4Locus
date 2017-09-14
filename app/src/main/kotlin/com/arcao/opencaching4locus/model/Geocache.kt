package com.arcao.opencaching4locus.model

import com.arcao.opencaching4locus.data.moshi.annotation.LocationField
import java.util.*

data class Geocache(
        val code : String,
        val name : String,
        @LocationField val location : Location,
        val type : String,
        val status : String,
        val needs_maintenance : Boolean = false,
        val url : String? = null,
        val owner: User? = null,
        val gc_code : String? = null,
        val is_found : Boolean = false,
        val is_not_found : Boolean = false,
        val is_watched : Boolean = false,
        val is_ignored : Boolean = false,
        val founds : Int = 0,
        val notfounds : Int = 0,
        val willattends : Int = 0,
        val size2 : String? = null,
        val difficulty : Float = 0F,
        val terrain : Float = 0F,
        val rating: Float? = null,
        val recommendations : Int = 0,
        val req_passwd: Boolean = false,
        val short_description: String? = null,
        val description : String? = null,
        val hint2 : String? = null,
        val images : List<Image> = emptyList(),
        val attr_acodes : List<String> = emptyList(),
        val attrnames : List<String> = emptyList(),
        val latest_logs : List<Log> = emptyList(),
        val my_notes : String? = null,
        val trackables : List<Trackable> = emptyList(),
        val alt_waypoints : List<Waypoint> = emptyList(),
        val country : String? = null,
        val state : String? = null,
        val last_found : Date? = null,
        val last_modified : Date? = null,
        val date_created : Date? = null,
        val date_hidden : Date? = null
) {
    companion object {
        val FIELD_CODE = "code"
        val FIELD_NAME = "name"
        val FIELD_LOCATION = "location"
        val FIELD_TYPE = "type"
        val FIELD_STATUS = "status"
        val FIELD_NEEDS_MAINTENANCE = "needs_maintenance"
        val FIELD_URL = "url"
        val FIELD_OWNER = "owner"
        val FIELD_GC_CODE = "gc_code"
        val FIELD_IS_FOUND = "is_found"
        val FIELD_IS_NOT_FOUND = "is_not_found"
        val FIELD_IS_WATCHED = "is_watched"
        val FIELD_IS_IGNORED = "is_ignored"
        val FIELD_FOUNDS = "founds"
        val FIELD_NOTFOUNDS = "notfounds"
        val FIELD_WILLATTENDS = "willattends"
        val FIELD_SIZE2 = "size2"
        val FIELD_DIFFICULTY = "difficulty"
        val FIELD_TERRAIN = "terrain"
        val FIELD_RATING = "rating"
        val FIELD_RECOMMENDATIONS = "recommendations"
        val FIELD_REQ_PASSWD = "req_passwd"
        val FIELD_SHORT_DESCRIPTION = "short_description"
        val FIELD_DESCRIPTION = "description"
        val FIELD_HINT2 = "hint2"
        val FIELD_IMAGES = "images"
        val FIELD_ATTR_ACODES = "attr_acodes"
        val FIELD_ATTRNAMES = "attrnames"
        val FIELD_LATEST_LOGS = "latest_logs"
        val FIELD_MY_NOTES = "my_notes"
        val FIELD_TRACKABLES = "trackables"
        val FIELD_ALT_WPTS = "alt_wpts"
        val FIELD_COUNTRY = "country"
        val FIELD_STATE = "state"
        val FIELD_LAST_FOUND = "last_found"
        val FIELD_LAST_MODIFIED = "last_modified"
        val FIELD_DATE_CREATED = "date_created"
        val FIELD_DATE_HIDDEN = "date_hidden"

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