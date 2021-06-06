package com.trkkr.trkkrclean.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trkkr.trkkrclean.architecture.DataState
import com.trkkr.trkkrclean.architecture.StateEvent
import com.trkkr.trkkrclean.domain.MiniPoi
import com.trkkr.trkkrclean.interactors.GetMiniPoi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    //private val getMiniPoi: GetMiniPoi
) : BaseViewModel<MapViewState>() {

    //Keeps track of MapBox Style, see https://docs.mapbox.com/android/maps/guides/styling-map/
    private val _mapBoxStyle = MutableLiveData<String>()
    val mapBoxStyle: LiveData<String> = _mapBoxStyle

    fun updateMapBoxStyle(style: String) {
        _mapBoxStyle.value = style
    }

    private val _miniPoi = MutableLiveData<MiniPoi>()
    val miniPoi: LiveData<MiniPoi> = _miniPoi

    fun updateMiniPoi(miniPoi: MiniPoi) {
        _miniPoi.value = miniPoi
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
/*
            is MapStateEvent.GetMiniPoiEvent -> {
                getMiniPoi.execute(stateEvent)
            }

 */
            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
    }

}
