package com.trkkr.trkkrclean.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trkkr.trkkrclean.architecture.DataState
import com.trkkr.trkkrclean.architecture.StateEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor() : BaseViewModel<MapViewState>() {

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

    fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<MapViewState>?> = when(stateEvent) {

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
    }

}
