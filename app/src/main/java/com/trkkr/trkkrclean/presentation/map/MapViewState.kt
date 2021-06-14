package com.trkkr.trkkrclean.presentation.map

import android.os.Parcelable
import com.trkkr.trkkrclean.architecture.ViewState
import com.trkkr.trkkrclean.domain.OsmNode
import com.trkkr.trkkrclean.domain.OsmWay
import com.trkkr.trkkrclean.domain.Poi
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapViewState (
    var poi: Poi? = null,
) : Parcelable, ViewState