package com.trkkr.trkkrclean.presentation

import android.os.Parcelable
import com.trkkr.trkkrclean.architecture.ViewState
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapViewState (
    var shit: String? = null
) : Parcelable, ViewState