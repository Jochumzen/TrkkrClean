package com.trkkr.trkkrclean.api.domain

import android.widget.ImageView
import com.google.gson.annotations.SerializedName
import com.mapbox.mapboxsdk.geometry.LatLng
import com.trkkr.trkkrclean.api.trkkr.ImageDto

data class MiniPoiDataClass (

    var images: List<ImageView>? = null,
    var name: String? = null,
    var category: String? = null,
    var distance: String? = null,
    var open: Boolean? = null,
    var myPosition: LatLng? = null,
    var poiPosition: LatLng? = null,
    var number: String? =null
)
