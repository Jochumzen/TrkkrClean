package com.trkkr.trkkrclean

import android.location.Location
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import java.lang.ref.WeakReference
import javax.inject.Inject

class TrkkrMapView @Inject constructor(
    private val mapper: Mapper
) : LifecycleObserver {

    private var weakMapView: WeakReference<MapView?>? = null

    fun setup(mapView: MapView?, callback: (MapboxMap) -> Unit) {
        weakMapView = WeakReference(mapView)
        Log.d("MyDebug", "mapview in setup: $mapView")
        mapView?.getMapAsync { mapboxMap ->
            Log.d("MyDebug", "mbm in setup: $mapboxMap")
            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                Log.d("MyDebug", "style in setup: $it")
                callback(mapboxMap)
            }
        }
    }

    fun flyToLocation(location: Location, mapboxMap: MapboxMap?) {
        val position = CameraPosition.Builder()
            .target(mapper.mapToLatLng(location))
            .zoom(15.0)
            .tilt(20.0)
            .build()

        mapboxMap?.animateCamera(
            CameraUpdateFactory.newCameraPosition(position),
            1000
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(source: LifecycleOwner, event: Lifecycle.Event) {
        val mapView = weakMapView?.get()
        @Suppress("NON_EXHAUSTIVE_WHEN")
        when (event) {
            Lifecycle.Event.ON_START   -> mapView?.onStart()
            Lifecycle.Event.ON_RESUME  -> mapView?.onResume()
            Lifecycle.Event.ON_PAUSE   -> mapView?.onPause()
            Lifecycle.Event.ON_STOP    -> mapView?.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView?.onDestroy()
        }
    }
}