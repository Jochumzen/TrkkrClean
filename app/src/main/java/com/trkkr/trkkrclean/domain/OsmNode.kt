package com.trkkr.trkkrclean.domain


data class OsmNode (
    var elements: List<Element>
) {
    data class Element(
        var type: String,
        var id: Long,
        var lat: Double,
        var lon: Double,
        var timestamp: String,
        var version: Int,
        var changeset: Long,
        var user: String,
        var uid: Long,
        var tags: Map<String, String>? = null
    )
}