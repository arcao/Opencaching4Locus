package com.arcao.opencaching4locus.model.enums

enum class LogType(val typeName: String) {
    /** A user found the cache (Non-Event caches). */
    FOUND_IT("Found it"),
    /** A user searched for, but couldn't find the cache (Non-Event caches). */
    DIDNT_FIND_IT("Didn't find it"),
    /** Any other log type. */
    COMMENT("Comment"),
    /** A user is planning to attend the event (for Event caches only). */
    WILL_ATTEND("Will attend"),
    /** A user has attended the event (for Event caches only). */
    ATTENDED("Attended"),
    /** log of this type indicates that the status of the cache was changed to "Temporarily unavailable" at that time, or it was confirmed as "Temporarily unavailable". Most probably the cache was not available to be found at that time, but it was expected to be repaired soon (and then, "Ready to search" log type would be submitted). */
    TEMPORARILY_UNAVAILABLE("Temporarily unavailable"),
    /** Log of this type indicates that the status of this cache was changed back to "Available", or it was confirmed as "Available". After some period of not being available the cache became ready to be found again. */
    READY_TO_SEARCH("Ready to search"),
    /** Log of this type indicates that the status of this cache was changed to "Archived" at this point in time. This usually means that the cache could not be found anymore at this time, and it was not expected to be repaired any time soon. */
    ARCHIVED("Archived"),
    /** The status of this cache was changed to "Locked" (similar to "Archived", but no more "Found it" log entries are allowed on "Locked" caches). */
    LOCKED("Locked"),
    /** The user stated that the cache was in need of maintenance. */
    NEEDS_MAINTENANCE("Needs maintenance"),
    /** The cache owner stated that he has performed the maintenance. */
    MAINTENANCE_PERFORMED("Maintenance performed"),
    /** The cache was moved to a different location. */
    MOVED("Moved"),
    /** A comment made by the official OC Team member. */
    OC_TEAM_COMMENT("OC Team comment");

    override fun toString(): String = typeName

    companion object {
        fun from(typeName: String?) = values().find { it.typeName == typeName } ?: COMMENT
    }
}