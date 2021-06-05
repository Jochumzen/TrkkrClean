package com.trkkr.trkkrclean

import com.google.gson.annotations.SerializedName
import com.trkkr.trkkrclean.api.trkkr.ImageDto

data class MiniPoiDataClass(

    @SerializedName("name")
    var name: String? = null,
    @SerializedName("category")
    var category: String? = null,
    @SerializedName("distance")
    var distance: String? = null,
    @SerializedName("open")
    var open: Boolean? = null

)
