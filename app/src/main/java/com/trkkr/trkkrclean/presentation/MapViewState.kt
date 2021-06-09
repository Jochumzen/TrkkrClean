package com.trkkr.trkkrclean.presentation

import android.os.Parcelable
import com.trkkr.trkkrclean.architecture.ViewState
import com.trkkr.trkkrclean.domain.OsmNode
import com.trkkr.trkkrclean.domain.OsmWay
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapViewState (
    var osmNode: OsmNode? = null,
    var osmWay: OsmWay? = null
) : Parcelable, ViewState