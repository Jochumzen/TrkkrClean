package com.trkkr.trkkrclean.api.trkkr.osmtype

import com.google.gson.annotations.SerializedName

data class OSMTypesDto (

    @SerializedName("result") 
    var result: String? = null,

    @SerializedName("types")
    var types: List<OsmPoiType>? = null
) {

    data class OsmPoiType (
        @SerializedName("osmTypeId")
        var osmTypeId: Int? = null,

        @SerializedName("osmKey")
        var osmKey: String? = null,

        @SerializedName("osmValue")
        var osmValue: String? = null,

        @SerializedName("name_en")
        var nameEn: String? = null,

        @SerializedName("name_sv")
        var nameSv: String? = null,

        @SerializedName("canBeNode")
        var canBeNode: Boolean? = null,

        @SerializedName("canBeWay")
        var canBeWay: Boolean? = null,

        @SerializedName("canBeArea")
        var canBeArea: Boolean? = null,

        @SerializedName("canBeCreated")
        var canBeCreated: Boolean? = null,

        @SerializedName("isCommon")
        var isCommon: Boolean? = null,

        @SerializedName("description_en")
        var descriptionEn: String? = null,

        @SerializedName("description_sv")
        var descriptionSv: String? = null,

        @SerializedName("imageName")
        var imageName: String? = null
    )
}



