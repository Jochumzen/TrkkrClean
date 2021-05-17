package com.trkkr.trkkrclean.API.OpenRouteService

import com.google.gson.annotations.SerializedName

data class DirectionsDto (
    @SerializedName("type")
    var type: String? = null,

    @SerializedName("features")
    var features: List<Feature>? = null,

    @SerializedName("bbox")
    var bbox: List<Double>? = null,

    @SerializedName("metadata")
    var metadata: Metadata? = null
) {

    data class Engine(
        @SerializedName("version")
        var version: String? = null,

        @SerializedName("build_date")
        var buildDate: String? = null,

        @SerializedName("graph_date")
        var graphDate: String? = null
    )

    data class Feature(
        @SerializedName("bbox")
        var bbox: List<Double>? = null,

        @SerializedName("type")
        var type: String? = null,

        @SerializedName("properties")
        var properties: Properties? = null,

        @SerializedName("geometry")
        var geometry: Geometry? = null
    )

    data class Geometry(
        @SerializedName("coordinates")
        var coordinates: List<List<Double>>? = null,

        @SerializedName("type")
        var type: String? = null
    )

    data class Metadata(
        @SerializedName("attribution")
        var attribution: String? = null,

        @SerializedName("service")
        var service: String? = null,

        @SerializedName("timestamp")
        var timestamp: Int? = null,

        @SerializedName("query")
        var query: Query? = null,
        @SerializedName("engine")

        var engine: Engine? = null
    )

    data class Properties(
        @SerializedName("segments")

        var segments: List<Segment>? = null,

        @SerializedName("summary")

        var summary: Summary? = null,

        @SerializedName("way_points")

        var wayPoints: List<Int>? = null
    )

    data class Query(
        @SerializedName("coordinates")
        var coordinates: List<List<Double>>? = null,

        @SerializedName("profile")
        var profile: String? = null,

        @SerializedName("format")
        var format: String? = null
    )

    data class Segment(
        @SerializedName("distance")
        var distance: Double? = null,

        @SerializedName("duration")
        var duration: Double? = null,

        @SerializedName("steps")
        var steps: List<Step>? = null
    )

    data class Step(
        @SerializedName("distance")
        var distance: Double? = null,

        @SerializedName("duration")
        var duration: Double? = null,

        @SerializedName("type")
        var type: Int? = null,

        @SerializedName("instruction")
        var instruction: String? = null,

        @SerializedName("name")
        var name: String? = null,

        @SerializedName("way_points")
        var wayPoints: List<Int>? = null
    )

    data class Summary(
        @SerializedName("distance")

        var distance: Double? = null,

        @SerializedName("duration")

        var duration: Double? = null
    )
}
