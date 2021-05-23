package com.trkkr.trkkrclean

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.util.Log
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import java.lang.ref.WeakReference
import javax.inject.Inject

class TrkkrLocationComponent @Inject constructor(
    private val permissionsUtil: PermissionsUtil,
    private val toaster: Toaster
)  {
    private var weakContext: WeakReference<Context?>? = null

    fun enableLocationComponent(
        context: Context,
        mapboxMap: MapboxMap?
    ) {

        weakContext = WeakReference(context)

        mapboxMap?.let {
            if (PermissionsManager.areLocationPermissionsGranted(context)) {
                onLocationPermissionGranted(
                    context = this@TrkkrLocationComponent.context,
                    mapboxMap = mapboxMap
                )
            } else {
                requestPermission(mapboxMap)
            }
        }
    }

    /*
    fun getLastKnownLocation(mapboxMap: MapboxMap?) : Location? {
        mapboxMap?.let{
            return mapboxMap.locationComponent.lastKnownLocation
        }
    }

     */


    private fun requestPermission(
        mapboxMap: MapboxMap
    ) {
        val permissionError = context?.getString(R.string.gps_toast_message)

        permissionsUtil.request(context as Activity) { granted ->
            if (granted) {
                onLocationPermissionGranted(
                    context = this@TrkkrLocationComponent.context,
                    mapboxMap = mapboxMap
                )
            } else {
                toaster.showToast(permissionError)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun onLocationPermissionGranted(context: Context?, mapboxMap: MapboxMap, ) {
        mapboxMap.style?.let { style ->
            if (context == null) return

            val locationComponent = mapboxMap.locationComponent

            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(context, style)
                    .build()

            locationComponent.activateLocationComponent(locationComponentActivationOptions)

            locationComponent.isLocationComponentEnabled = true

            locationComponent.cameraMode = CameraMode.TRACKING

            locationComponent.renderMode = RenderMode.COMPASS

            Log.d("MyDebug", "Location component is enabled.")
        }
    }

    private val context
        get() = weakContext?.get()
}