package com.trkkr.trkkrclean.presentation

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mapbox.geojson.Feature
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    private val _mapBoxStyle = MutableLiveData<String>()
    val mapBoxStyle: LiveData<String> = _mapBoxStyle

    fun updateMapBoxStyle(style: String) {
        _mapBoxStyle.value = style
    }

    //Whether or not the Mapbox LocationComponent is enabled or not
    private var locationComponentEnabled : Boolean = false

    fun setLocationComponentEnabled(value: Boolean) {
        locationComponentEnabled = value
    }

    fun getLocationComponentEnabled() : Boolean {
        return locationComponentEnabled
    }

    fun setClickedFeature(feature: Feature) {

    }

}
