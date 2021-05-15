package com.trkkr.trkkrclean

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.mapbox.mapboxsdk.maps.MapView
import java.lang.NullPointerException

class TrkkrMapboxFake : ITrkkrMapbox {

    private var frameLayout : FrameLayout? = null

    override fun getInstance(context: Context, accessToken: String) {

    }

    override fun createMapView(context: Context) {
        frameLayout = FrameLayout(context)

        val mapView = ImageView(context)
        mapView.setImageResource(R.mipmap.ic_launcher)
        mapView.layoutParams = ViewGroup.LayoutParams(1100, 1600)
        mapView.isClickable = true

        mapView.setOnClickListener {
            Log.d("MyDebug", "Hello!")
        }
        frameLayout!!.addView(mapView)
    }

    override fun getMapView(): FrameLayout {
        if (frameLayout == null) {
            throw NullPointerException()
        } else {
            return frameLayout!!
        }
    }

    override fun setStyle(savedInstanceState: Bundle?) {
    }
}