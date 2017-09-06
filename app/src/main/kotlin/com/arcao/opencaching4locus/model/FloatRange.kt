package com.arcao.opencaching4locus.model

data class FloatRange (
        val min : Float,
        val max : Float
) {
    init {
        if (min < 0)
            throw IllegalArgumentException("Min parameter shall be positive")
        if (max < 0)
            throw IllegalArgumentException("Max parameter shall be positive")
        if (min > max)
            throw IllegalArgumentException("Max parameter shall be greater than min parameter")
    }

    override fun toString(): String {
        return "$min-$max"
    }
}