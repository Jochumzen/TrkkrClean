package com.trkkr.trkkrclean

import android.location.Location
import com.mapbox.mapboxsdk.geometry.LatLng

class Mapper {

    fun mapToLatLng(location: Location): LatLng {
        return LatLng(location.latitude, location.longitude)
    }
}