package com.arcao.opencaching4locus.model

import java.util.*

data class Geocache(
        val code : String,
        val name : String,
        val location : Location,
        val type : String,
        val status : String,
        val needs_maintenance : Boolean?,
        val url : String?,
        val owner: User?,
        val gc_code : String?,
        val is_found : Boolean,
        val is_not_found : Boolean?,
        val is_watched : Boolean?,
        val is_ignored : Boolean?,
        val founds : Int?,
        val notfounds : Int?,
        val willattends : Int?,
        val size2 : Int,
        val difficulty : Float,
        val terrain : Float,
        val rating: Float?,
        val recommendations : Int?,
        val req_passwd: Boolean?,
        val short_description: String?,
        val description : String?,
        val hint2 : String,
        val images : List<Image>?,
        val attr_acodes : List<String>?,
        val attrnames : List<String>?,
        val latest_logs : List<Log>,
        val my_notes : String?,
        val trackables : List<Trackable>?,
        val alt_waypoints : List<Waypoint>?,
        val country : String?,
        val state : String?,
        val last_found : Date?,
        val last_modified : Date?,
        val date_created : Date?,
        val date_hidden : Date?
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
        val FIELD_ALT_WAYPOINTS = "alt_waypoints"
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
                FIELD_IS_FOUND,
                FIELD_IS_NOT_FOUND,
                FIELD_IS_WATCHED,
                FIELD_IS_IGNORED,
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
                FIELD_MY_NOTES,
                FIELD_TRACKABLES,
                FIELD_ALT_WAYPOINTS,
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