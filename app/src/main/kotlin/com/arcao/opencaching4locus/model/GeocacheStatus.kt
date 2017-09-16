package com.arcao.opencaching4locus.model

enum class GeocacheStatus(val statusName: String) {
    AVAILABLE("Available"),
    TEMPORARILY_UNAVAILABLE("Temporarily unavailable"),
    ARCHIVED("Archived");

    override fun toString(): String = statusName

    companion object {
        fun from(statusName: String?) = values().find { it.statusName == statusName } ?: AVAILABLE
    }

}