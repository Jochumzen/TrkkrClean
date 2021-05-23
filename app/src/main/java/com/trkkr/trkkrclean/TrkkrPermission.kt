package com.trkkr.trkkrclean

import android.app.Activity
import android.content.Context
import android.util.Log
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.maps.MapboxMap
import java.lang.ref.WeakReference
import javax.inject.Inject

class TrkkrPermission @Inject constructor(
    private val permissionsUtil: PermissionsUtil
) {

    private var weakContext: WeakReference<Context?>? = null

    fun go(
        context: Context
    ) {
        weakContext = WeakReference(context)
        if (PermissionsManager.areLocationPermissionsGranted(context)) {
            Log.d("MyDebug", "PermissionsGranted")

        } else {
            Log.d("MyDebug", "PermissionsNotGranted")
            requestPermission()
        }
    }

    private fun requestPermission() {

        permissionsUtil.request(context as Activity) { granted ->
            if (granted) {

            } else {
                //toaster.showToast(permissionError)
                //viewModel.updateCurrentLocationStrip(null)
            }
        }
    }

    private val context
        get() = weakContext?.get()

}