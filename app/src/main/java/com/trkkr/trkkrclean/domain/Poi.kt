package com.trkkr.trkkrclean.domain

import android.os.Parcelable
import ch.poole.openinghoursparser.OpeningHoursParseException
import ch.poole.openinghoursparser.OpeningHoursParser
import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.geometry.LatLng
import com.trkkr.trkkrclean.utilities.evaluate
import kotlinx.parcelize.Parcelize
import java.io.ByteArrayInputStream
import java.util.*

@Parcelize
data class Poi (
    var feature: Feature? = null,
    var mapClicked: LatLng? = null,
    var userLocation: LatLng? = null,
    var images: List<String>? = null,
    var osmType: OsmType? = null,
    var osmNode: OsmNode? = null,
    var osmWay: OsmWay? = null,
    var distance: Double? = null,
): Parcelable {

    fun isPoiOpen() : Boolean? {
        return osmType?.let {
            when (it) {
                OsmType.NODE -> osmNode?.let { node ->
                    isPoiOpen(node.tags)
                }
                OsmType.WAY -> osmWay?.let { way ->
                    isPoiOpen(way.tags)
                }
                else -> null
            }
        }
    }

    private fun isPoiOpen(openingHours: String) : Boolean? {
        return try {
            val parser =
                OpeningHoursParser(ByteArrayInputStream(openingHours.toByteArray()))
            val rules = parser.rules(false)
            rules.evaluate(Date())
        } catch (pex: OpeningHoursParseException) {
            null
        }
    }

    private fun isPoiOpen(tags: Map<String, String>?) : Boolean? {
        return tags?.get("opening_hours")?.let { openingHours ->
            isPoiOpen(openingHours)
        }
    }
}
