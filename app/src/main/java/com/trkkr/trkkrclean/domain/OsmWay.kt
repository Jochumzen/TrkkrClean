package com.trkkr.trkkrclean.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OsmWay (
    var type: String,
    var id: Long,
    var timestamp: String,
    var version: Int,
    var changeset: Long,
    var user: String,
    var uid: Long,
    var nodes: List<Long>? = null,
    var tags: Map<String, String>? = null
) : Parcelable
