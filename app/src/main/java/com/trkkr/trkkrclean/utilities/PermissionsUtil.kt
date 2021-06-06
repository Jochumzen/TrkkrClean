package com.trkkr.trkkrclean.utilities

import android.app.Activity
import android.util.Log
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionsUtil @Inject constructor(): PermissionsListener {

    private val permissionsManager: PermissionsManager by lazy {
        PermissionsManager(this)
    }

    private var onGrantedCallBack: ((Boolean) -> Unit)? = null

    fun request(
        activity: Activity,
        onGrantedCallBack: ((Boolean) -> Unit)? = null
    ) {
        this.onGrantedCallBack = onGrantedCallBack

        Log.d("MyDebug", "PU: Requesting perms. OnGrCN: $onGrantedCallBack")
        permissionsManager.requestLocationPermissions(activity)
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        Log.d("MyDebug", "ExplNeeded")
    }

    override fun onPermissionResult(granted: Boolean) {
        Log.d("MyDebug", "PU: perms result. OnGrCN: $onGrantedCallBack, granted: $granted")
        onGrantedCallBack?.invoke(granted)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}