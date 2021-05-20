package com.trkkr.trkkrclean.api.trkkr

import com.google.gson.annotations.SerializedName

data class ImageDto(

    @SerializedName("success") 
    var success: Boolean? = null,
    @SerializedName("errorMessage")
    var errorMessage: Any? = null,

    @SerializedName("allConnections")
    var allConnections: List<OsmImageConnection>? = null
) {

    data class OsmImageConnection (
        @SerializedName("osmImageConnectionId")
        var osmImageConnectionId: Int? = null,

        @SerializedName("imageSource")
        var imageSource: Int? = null,

        @SerializedName("imageId")
        var imageId: String? = null,

        @SerializedName("osmElementType")
        var osmElementType: Int? = null,

        @SerializedName("osmElementId")
        var osmElementId: String? = null,

        @SerializedName("imageOrder")
        var imageOrder: Int? = null
    )
}
