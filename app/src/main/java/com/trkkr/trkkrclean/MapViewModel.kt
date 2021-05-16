package com.trkkr.trkkrclean

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MapViewModel
@Inject
constructor(

) : ViewModel() {

    private val _locationFlyer = MutableLiveData<Location>()
    val locationFlyer: LiveData<Location> = _locationFlyer

    fun updateLocationFlyer(location: Location) {
        _locationFlyer.value = location
    }
}
