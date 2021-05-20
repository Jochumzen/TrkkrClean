package com.trkkr.trkkrclean.api.ors.search

import com.google.gson.annotations.SerializedName

data class SearchDto(

    @SerializedName("geocoding")

    var geocoding: Geocoding? = null,

    @SerializedName("type")

    var type: String? = null,

    @SerializedName("features")

    var features: List<Feature>? =
        null,

    @SerializedName("bbox")

    var bbox: List<Double>? = null
) {

    data class Engine(
        @SerializedName("name")
        var name: String? = null,

        @SerializedName("author")
        var author: String? = null,

        @SerializedName("version")
        var version: String? = null,
    )

    data class Feature(
        @SerializedName("type")

        var type: String? = null,

        @SerializedName("geometry")

        var geometry: Geometry? = null,

        @SerializedName("properties")

        var properties: Properties? = null,

        @SerializedName("bbox")

        var bbox: List<Double>? = null,

        //We calculate this one
        var distance: Double? = null,
    )

    data class Geocoding (
        @SerializedName("version")
        var version: String? = null,

        @SerializedName("attribution")
        var attribution: String? = null,

        @SerializedName("query")
        var query: Query? = null,

        @SerializedName("warnings")

        var warnings: List<String>? = null,

        @SerializedName("engine")

        var engine: Engine? = null,

        @SerializedName("timestamp")
        var timestamp: Long? = null,
    )

    data class Geometry (
        @SerializedName("type")
        var type: String? = null,

        @SerializedName("coordinates")
        var coordinates: List<Double>? = null,
    )

    data class Lang (
        @SerializedName("name")
        var name: String? = null,

        @SerializedName("iso6391")
        var iso6391: String? = null,

        @SerializedName("iso6393")
        var iso6393: String? = null,

        @SerializedName("defaulted")
        var defaulted: Boolean? = null,
    )

    data class ParsedText (
        @SerializedName("subject")
        var subject: String? = null,
    )

    data class Properties (
        @SerializedName("id")
        var id: String? = null,

        @SerializedName("gid")
        var gid: String? = null,

        @SerializedName("layer")
        var layer: String? = null,

        @SerializedName("source")
        var source: String? = null,

        @SerializedName("source_id")
        var sourceId: String? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("confidence")
        var confidence: Double? = null,

        @SerializedName("distance")
        var distance: Double? = null,

        @SerializedName("match_type")
        var matchType: String? = null,

        @SerializedName("accuracy")
        var accuracy: String? = null,

        @SerializedName("country")
        var country: String? = null,

        @SerializedName("country_gid")
        var countryGid: String? = null,

        @SerializedName("country_a")
        var countryA: String? = null,

        @SerializedName("region")
        var region: String? = null,

        @SerializedName("region_gid")
        var regionGid: String? = null,

        @SerializedName("region_a")
        var regionA: String? = null,

        @SerializedName("county")
        var county: String? = null,

        @SerializedName("county_gid")
        var countyGid: String? = null,

        @SerializedName("locality")
        var locality: String? = null,

        @SerializedName("locality_gid")
        var localityGid: String? = null,

        @SerializedName("localadmin")
        var localadmin: String? = null,

        @SerializedName("localadmin_gid")
        var localadminGid: String? = null,

        @SerializedName("continent")
        var continent: String? = null,

        @SerializedName("continent_gid")
        var continentGid: String? = null,

        @SerializedName("label")
        var label: String? = null,

        @SerializedName("neighbourhood")
        var neighbourhood: String? = null,

        @SerializedName("neighbourhood_gid")
        var neighbourhoodGid: String? = null,

        @SerializedName("county_a")
        var countyA: String? = null,
    )

    data class Query (
        @SerializedName("text")
        var text: String? = null,

        @SerializedName("parser")
        var parser: String? = null,

        @SerializedName("parsed_text")
        var parsedText: ParsedText? = null,

        @SerializedName("size")
        var size: Int? = null,

        @SerializedName("layers")
        var layers: List<String>? = null,

        @SerializedName("sources")
        var sources: List<String>? = null,

        @SerializedName("private")
        var private: Boolean? = null,

        @SerializedName("focus.point.lat")
        var lat: Int? = null,

        @SerializedName("focus.point.lon")
        var lon: Int? = null,

        @SerializedName("boundary.country")
        var bc: List<String>? = null,

        @SerializedName("lang")
        var lang: Lang? = null,

        @SerializedName("querySize")
        var querySize: Int? = null
    )
}