package com.trkkr.trkkrclean.api.wikipedia

import com.google.gson.annotations.SerializedName

data class WikipediaDto(

    @SerializedName("batchcomplete") 
    var batchcomplete: String? = null,

    @SerializedName("query")
    var query: WikipediaQuery? = null
) {

    data class WikipediaQuery (
        @SerializedName("normalized")
        var normalized: List<WikipediaNormalized>? = null,

        @SerializedName("pages")
        var pages: WikipediaPages? = null
    )

    data class WikipediaNormalized (
        @SerializedName("from")

        var from: String? = null,

        @SerializedName("to")

        var to: String? = null
    )

    data class WikipediaPages (
        @SerializedName("page")

        var page: WikipediaPage? = null
    )

    data class WikipediaPage (
        @SerializedName("pageid")

        var pageid: Int? = null,

        @SerializedName("ns")

        var ns: Int? = null,

        @SerializedName("title")

        var title: String? = null,

        @SerializedName("revisions")

        var revisions: List<WikipediaRevision>? = null
    )

    data class WikipediaRevision (
        @SerializedName("slots")

        var slots: WikipediaSlots? = null
    )

    data class WikipediaSlots (
        @SerializedName("main")

        var main: WikipediaMain? = null
    )

    data class WikipediaMain (
        @SerializedName("contentmodel")

        var contentmodel: String? = null,

        @SerializedName("contentformat")

        var contentformat: String? = null,

        @SerializedName("main")

        var main: String? = null
    )
}