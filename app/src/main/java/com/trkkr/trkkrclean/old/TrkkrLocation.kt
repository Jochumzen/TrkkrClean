package com.trkkr.trkkrclean.old

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
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.trkkr.trkkrclean.utilities.PermissionsUtil
import com.trkkr.trkkrclean.presentation.MapViewModel
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import java.lang.ref.WeakReference
import javax.inject.Inject


//Try to figure out how to inject the ViewModel into the constructor instead of sending it as an argument in enableLocationComponent
@ActivityScoped
class TrkkrLocation @Inject constructor(
    private val permissionsUtil: PermissionsUtil,
    private val locationEngine: LocationEngine
) : LocationEngineCallback<LocationEngineResult> {


    private var weakContext: WeakReference<Context?>? = null
    private var flyToLocation = false
    private var mapViewModel: MapViewModel? = null

    //private val mapViewModel: MapViewModel by activityViewModels()

    fun centerMapOnCurrentLocation(
        context: Context
    ) {
        weakContext = WeakReference(context)

        if (PermissionsManager.areLocationPermissionsGranted(context)) {
            requestLocationUpdate()
        } else {
            Log.d("MyDebug", "PermissionsNotGranted")
            requestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdate() {
        val request = LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build()

        locationEngine.requestLocationUpdates(request, this, Looper.getMainLooper())
    }

    override fun onSuccess(result: LocationEngineResult?) {
        context?.let {
            val lastLocation = result?.lastLocation ?: return
            Log.d("MyDebug", "LocationEngine Success: $lastLocation")
            //mapViewModel?.updateLocationFlyer(lastLocation)
        }
    }

    override fun onFailure(exception: Exception) {
        Log.d("MyDebug", "onFailure")
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(mapboxMap: MapboxMap?) {
        context?.let { context ->
            if (PermissionsManager.areLocationPermissionsGranted(context)) {
                locationEngine.getLastLocation(this)
            } else {
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        permissionsUtil.request(context as Activity) { granted ->
            if (granted) {
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