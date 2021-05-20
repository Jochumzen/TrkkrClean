package com.trkkr.trkkrclean.api.wikidata

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WikidataDto (

    @SerializedName("head") 
    var head: WikiDataHead? = null,

    @SerializedName("results")
    var results: WikiDataResults? = null
) {
    data class WikiDataHead (
        @SerializedName("vars")
        var vars: List<String>? = null //new ArrayList<>();
    )

    data class WikiDataResults (
        @SerializedName("bindings")
        var bindings: List<WikiDataBinding>? = null
    )

    data class WikiDataBinding (
        @SerializedName("lang")
        var lang: WikiDataLang? = null,

        @SerializedName("name")
        var name: WikiDataName? = null
    )

    data class WikiDataName (
        @SerializedName("xml:lang")
        var xmlLang: String? = null,

        @SerializedName("type")
        var type: String? = null,

        @SerializedName("value")
        var value: Expose? = null
    )

    data class WikiDataLang (
        @SerializedName("type")
        var type: String? = null,

        @SerializedName("value")
        var value: String? = null
    )
}