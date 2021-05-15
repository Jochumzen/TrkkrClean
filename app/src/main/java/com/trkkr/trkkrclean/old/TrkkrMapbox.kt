package com.trkkr.trkkrclean.old

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMapOptions
import com.mapbox.mapboxsdk.maps.Style
import java.lang.NullPointerException
import javax.inject.Inject

/*
class TrkkrMapbox : ITrkkrMapbox
{

    private var mapView : MapView? = null

    override fun getInstance(context: Context, accessToken: String) {
        Mapbox.getInstance(context, accessToken)
    }

    override fun createMapView(context: Context) {
        val options = MapboxMapOptions.createFromAttributes(context, null)
        val position = CameraPosition.Builder()
            .target(LatLng(51.50550, -0.07520))
            .zoom(10.0)
            .tilt(20.0)
            .build()
        options.camera(position)

        mapView = MapView(context, options)
    }

    override fun getMapView(): FrameLayout {
        if (mapView == null) {
            throw NullPointerException()
        } else {
            return mapView!!
        }
    }

    override fun setStyle(savedInstanceState: Bundle?) {
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
            }
        }
    }
}

 */