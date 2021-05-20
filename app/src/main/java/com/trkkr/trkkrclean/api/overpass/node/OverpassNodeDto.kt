package com.trkkr.trkkrclean.api.overpass.node

import com.google.gson.annotations.SerializedName

data class OverpassNodeDto (
    var version: Double,
    var generator: String,
    var osm3s: Osm3s,
    var elements: List<Element>
){
    data class Osm3s(
        @SerializedName("timestamp_osm_base")
        var timestampOsmBase: String,
        var copyright: String
    )

    data class Element(
        var type: String,
        var id: Long,
        var lat: Double,
        var lon: Double,
        var tags: Map<String, String>? = null
    )
}