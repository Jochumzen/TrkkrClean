package com.trkkr.trkkrclean.api.domain


data class OsmNode (
    var version: Double,
    var elements: List<Element>
) {
    data class Element(
        var type: String,
        var id: Long,
        var lat: Double,
        var lon: Double,
        var tags: Map<String, String>? = null
    )
}