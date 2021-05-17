package com.trkkr.trkkrclean

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Looper
import android.util.Log
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import java.lang.Exception
import java.lang.ref.WeakReference
import javax.inject.Inject


//Try to figure out how to inject the ViewModel into the constructor instead of sending it as an argument in enableLocationComponent
class TrkkrLocation @Inject constructor(
    private val permissionsUtil: PermissionsUtil,
    private val locationEngine: LocationEngine
) : LocationEngineCallback<LocationEngineResult> {


    private var weakContext: WeakReference<Context?>? = null
    private var flyToLocation = false
    private var mapViewModel: MapViewModel? = null

    //private val mapViewModel: MapViewModel by activityViewModels()
    fun enableLocationComponent(
        mapViewModel: MapViewModel,
        context: Context,
        mapboxMap: MapboxMap?,
        flyToLocation: Boolean
    ) {
        Log.d("MyDebug", "enableLocationComponent. mbm: $mapboxMap")

        weakContext = WeakReference(context)

        this@TrkkrLocation.mapViewModel = mapViewModel

        mapboxMap?.let {
            if (PermissionsManager.areLocationPermissionsGranted(context)) {
                Log.d("MyDebug", "PermissionsGranted")
                onLocationPermissionGranted(
                    mapboxMap = mapboxMap,
                    context = this@TrkkrLocation.context,
                    flyToLocation = flyToLocation
                )
            } else {
                Log.d("MyDebug", "PermissionsNotGranted")
                requestPermission(mapboxMap)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdate() {
        val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build()

        locationEngine.requestLocationUpdates(request, this, Looper.getMainLooper())
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(mapboxMap: MapboxMap?) {
        context?.let { context ->
            if (PermissionsManager.areLocationPermissionsGranted(context)) {
                locationEngine.getLastLocation(this)
            } else {
                mapboxMap?.let { requestPermission(it) }
            }
        }
    }

    override fun onSuccess(result: LocationEngineResult?) {
        context?.let {
            val lastLocation = result?.lastLocation ?: return

            if (flyToLocation) {
                flyToLocation = false
                mapViewModel?.updateLocationFlyer(lastLocation)
            }
        }
    }

    override fun onFailure(exception: Exception) {
        //TODO("Not yet implemented")
    }

    private fun requestPermission(mapboxMap: MapboxMap) {
        val permissionError = context?.getString(R.string.gps_toast_message)

        permissionsUtil.request(context as Activity) { granted ->
            if (granted) {
                onLocationPermissionGranted(
                    mapboxMap = mapboxMap,
                    context = this@TrkkrLocation.context,
                    flyToLocation = flyToLocation
                )
            } else {
                //toaster.showToast(permissionError)
                //viewModel.updateCurrentLocationStrip(null)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun onLocationPermissionGranted(
        mapboxMap: MapboxMap,
        context: Context?,
        flyToLocation: Boolean
    ) {
        mapboxMap.style?.let { style ->

            Log.d("MyDebug", "onLocationPermissionGranted: $style")

            if (context == null) return

            val locationComponent = mapboxMap.locationComponent

            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(context, style)
                    //.useDefaultLocationEngine(false)
                    .build()

            locationComponent.activateLocationComponent(locationComponentActivationOptions)

            locationComponent.isLocationComponentEnabled = true

            locationComponent.cameraMode = CameraMode.TRACKING

            locationComponent.renderMode = RenderMode.COMPASS

            this.flyToLocation = flyToLocation

            if (flyToLocation) {
                requestLocationUpdate()
            }
        }

    }

    private val context
        get() = weakContext?.get()

    companion object {
        private const val DEFAULT_INTERVAL_IN_MILLISECONDS = 3000L
        private const val DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5
    }

}