package com.trkkr.trkkrclean.api.domain

import com.google.gson.annotations.SerializedName
import com.trkkr.trkkrclean.api.trkkr.ImageDto

data class MiniPoiDataClass (

    var name: String? = null,
    var category: String? = null,
    var distance: String? = null,
    var open: Boolean? = null
)
