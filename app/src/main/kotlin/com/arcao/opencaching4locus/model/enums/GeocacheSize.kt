package com.arcao.opencaching4locus.model.enums

enum class GeocacheSize(val sizeName: String) {
    NONE("none"),
    NANO("nano"),
    MICRO("micro"),
    SMALL("small"),
    REGULAR("regular"),
    LARGE("large"),
    XLARGE("xlarge"),
    OTHER("other");

    override fun toString(): String = sizeName

    companion object {
        fun from(sizeName: String?) = values().find { it.sizeName == sizeName } ?: OTHER
    }
}