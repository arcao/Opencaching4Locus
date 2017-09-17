package com.arcao.opencaching4locus.model.response

data class GeocacheSearchResponse(
        val results: List<String>,
        val more: Boolean = false
)