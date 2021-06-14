package com.trkkr.trkkrclean.utilities

import android.location.Location
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.turf.TurfMeasurement

class Mapper {

    fun mapToLatLng(location: Location): LatLng {
        return LatLng(location.latitude, location.longitude)
    }

    fun mapToPoint(latLng: LatLng?): Point? {

        return latLng?.let {Point.fromLngLat(latLng.longitude, latLng.latitude)}

    }

    fun distance(pos1: LatLng?, pos2: LatLng?): Double? {
        val pos1Point = mapToPoint(pos1)
        val pos2Point = mapToPoint(pos2)
        return if (pos1Point != null && pos2Point != null)  TurfMeasurement.distance(pos1Point, pos2Point) else null
    }
}