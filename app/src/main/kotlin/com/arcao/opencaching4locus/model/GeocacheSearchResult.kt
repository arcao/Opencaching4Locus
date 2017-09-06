package com.arcao.opencaching4locus.model

import java.util.*

data class GeocacheSearchResult(
        val results : Array<String>,
        val more : Boolean?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeocacheSearchResult

        if (!Arrays.equals(results, other.results)) return false
        if (more != other.more) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(results)
        result = 31 * result + (more?.hashCode() ?: 0)
        return result
    }

}