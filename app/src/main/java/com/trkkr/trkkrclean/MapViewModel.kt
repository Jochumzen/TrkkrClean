package com.trkkr.trkkrclean

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel
@Inject
constructor(
    private val trkkrMapbox: ITrkkrMapbox
) : ViewModel() {


    fun getInstance(context: Context, accessToken: String) {
        trkkrMapbox.getInstance(context, accessToken)
    }

    fun createMapView(context: Context) {
        trkkrMapbox.createMapView(context)
    }

    fun setStyle(savedInstanceState: Bundle?) {
        trkkrMapbox.setStyle(savedInstanceState)
    }

    fun getMapView() : FrameLayout {
        return trkkrMapbox.getMapView()
    }



}