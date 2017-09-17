package com.arcao.opencaching4locus.model.enums

enum class GeocacheStatus(val statusName: String) {
    /** Cache is available and ready for search */
    AVAILABLE("Available"),
    /** Cache is unavailable (probably cannot be found) but may be restored */
    TEMPORARILY_UNAVAILABLE("Temporarily unavailable"),
    /** Cache is permanently unavailable (moved to the archives) */
    ARCHIVED("Archived");

    override fun toString(): String = statusName

    companion object {
        fun from(statusName: String?) = values().find { it.statusName == statusName } ?: AVAILABLE
    }

}