package com.trkkr.trkkrclean.utilities

import android.util.Log
import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.trkkr.trkkrclean.domain.OsmType
import com.trkkr.trkkrclean.presentation.map.MapStateEvent

fun Feature.getOSMId(): String?
{
        return this.id()?.dropLast(1)
}

fun Feature.getOSMType(): OsmType?
{
    val lastChar = this.id()?.lastOrNull()

    return if(lastChar != null)
        when (lastChar) {
            '0' -> OsmType.NODE
            '1' -> OsmType.WAY
            '2' -> OsmType.RELATION
            else -> OsmType.UNKNOWN
        }
    else
        null
}


fun MapboxMap?.flyToLastKnownLocation() {

    val mapper = Mapper()
    if(this != null && locationComponent.isLocationComponentActivated) {

        locationComponent.lastKnownLocation?.let {
            val position = CameraPosition.Builder()
                .target(mapper.mapToLatLng(it))
                .zoom(15.0)
                .tilt(20.0)
                .build()

            animateCamera(
                CameraUpdateFactory.newCameraPosition(position),
                1000
            )
        }
    }
}

fun MapboxMap?.flyToPosition(position: CameraPosition) {

    this?.animateCamera(
        CameraUpdateFactory.newCameraPosition(position),
        1000
    )
}

fun MapboxMap?.listenForPoiClick(mapStateEventCallback: ((MapStateEvent?) -> Unit)? = null) {

    val mapper = Mapper()
    this?.addOnMapClickListener { latLng ->

        val screenPoint = projection.toScreenLocation(latLng)

        Log.d("MyDebug", "MapFragment(addOnMapClickListener). Map clicked. latLng: $latLng screenPoint: $screenPoint")

        val features = queryRenderedFeatures(screenPoint, "poi-label")

        if (features.size > 0) {

            //Assume that there can be at most 1 "poi-label" Feature
            Log.d("MyDebug", "MapFragment(addOnMapClickListener). Feature clicked on: $features[0]")

            var userLocation : LatLng? = null
            if(locationComponent.isLocationComponentActivated) {
                userLocation = locationComponent.lastKnownLocation?.let { mapper.mapToLatLng(it) }
            }

            Log.d("MyDebug", "MapFragment(addOnMapClickListener). The user is at location: $userLocation. ")

            val mapStateEvent : MapStateEvent.GetPoiEvent =
                MapStateEvent.GetPoiEvent(latLng, features[0], userLocation)

            mapStateEventCallback?.invoke(mapStateEvent)


        } else {
            Log.d("MyDebug", "MapFragment(addOnMapClickListener). No poi-label features at this point. Goodbye.")

            mapStateEventCallback?.invoke(null)
        }


        true
    }
}