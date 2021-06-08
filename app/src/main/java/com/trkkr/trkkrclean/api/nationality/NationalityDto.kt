package com.example.play71.network

import com.google.gson.annotations.SerializedName

data class NationalityDto (

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("uid")
    var uid: String? = null,

    @SerializedName("nationality")
    var nationality: String? = null,

    @SerializedName("language")
    var language: String? = null,

    @SerializedName("capital")
    var capital: String? = null,

    @SerializedName("national_sport")
    var national_sport: String? = null,

    @SerializedName("flag")
    var flag: String? = null
) {

}