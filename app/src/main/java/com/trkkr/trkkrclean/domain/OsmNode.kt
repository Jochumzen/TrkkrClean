package com.trkkr.trkkrclean.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OsmNode (
    var type: String,
    var id: Long,
    var lat: Double,
    var lon: Double,
    var timestamp: String,
    var version: Int,
    var changeset: Long,
    var user: String,
    var uid: Long,
    var tags: Map<String, String>? = null
) : Parcelable
