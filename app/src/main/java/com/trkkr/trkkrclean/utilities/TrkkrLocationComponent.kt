package com.trkkr.trkkrclean.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.trkkr.trkkrclean.R
import java.lang.ref.WeakReference
import javax.inject.Inject

class TrkkrLocationComponent @Inject constructor(
    private val permissionsUtil: PermissionsUtil,
    private val toaster: Toaster
)  {
    private var weakContext: WeakReference<Context?>? = null

    private var locationComponentEnabled: ((Boolean) -> Unit)? = null

    fun enableLocationComponent(
        context: Context,
        mapboxMap: MapboxMap?,
        locationComponentEnabled: ((Boolean) -> Unit)? = null
    ) {

        weakContext = WeakReference(context)
        this.locationComponentEnabled = locationComponentEnabled
        mapboxMap?.let {
            if (PermissionsManager.areLocationPermissionsGranted(context)) {

                onLocationPermissionGranted(
                    context = this@TrkkrLocationComponent.context,
                    mapboxMap = mapboxMap
                )
                locationComponentEnabled?.invoke(true)
            } else {
                Log.d("MyDebug", "No permission.")
                requestPermission(mapboxMap)
            }
        }
    }


    private fun requestPermission(
        mapboxMap: MapboxMap
    ) {
        val permissionError = context?.getString(R.string.gps_toast_message)
        Log.d("MyDebug", "TLC: Requesting perms.")
        permissionsUtil.request(context as Activity) { granted ->
            Log.d("MyDebug", "TLC callback. granted: $granted")

            if (granted) {
                onLocationPermissionGranted(
                    context = this@TrkkrLocationComponent.context,
                    mapboxMap = mapboxMap
                )
                locationComponentEnabled?.invoke(true)
            } else {
                toaster.showToast(permissionError)
                locationComponentEnabled?.invoke(false)
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