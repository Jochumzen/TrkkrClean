package com.trkkr.trkkrclean.domain

data class MiniPoi (
    var name: String? = null,
    var category: String? = null,
    var distance: String? = null,
    var open: Boolean? = null,
    var images: List<String>? = null
)
