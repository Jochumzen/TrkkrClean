package com.trkkr.trkkrclean.presentation.search

import android.os.Parcelable
import com.trkkr.trkkrclean.architecture.ViewState
import kotlinx.parcelize.Parcelize

@Parcelize
data class  SearchViewState(
    var shit: String
) : Parcelable, ViewState {

}