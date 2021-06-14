package com.trkkr.trkkrclean.presentation.search


import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

import com.mapbox.mapboxsdk.maps.*

import java.lang.ref.WeakReference
import javax.inject.Inject


class TrkkrSearchMapView @Inject constructor() : LifecycleObserver {

    private var weakMapView: WeakReference<MapView?>? = null

    fun setup(
        mapView: MapView?,
        callback: (MapboxMap) -> Unit
    ) {
        weakMapView = WeakReference(mapView)

        mapView?.getMapAsync { mapboxMap ->

            Log.d("MyDebug", "TrkkrSearchMapView(setup). We have a MapboxMap in Search: $mapboxMap")

            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                Log.d("MyDebug", "TrkkrMap(setup). MapboxMap complete. Style: $it")
                callback(mapboxMap)
            }
        }
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