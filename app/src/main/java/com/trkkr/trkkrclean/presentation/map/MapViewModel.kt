package com.trkkr.trkkrclean.presentation.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trkkr.trkkrclean.architecture.DataState
import com.trkkr.trkkrclean.architecture.StateEvent
import com.trkkr.trkkrclean.domain.Poi
import com.trkkr.trkkrclean.interactors.GetPoi
import com.trkkr.trkkrclean.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getPoi: GetPoi
) : BaseViewModel<MapViewState>() {

    //Keeps track of MapBox Style, see https://docs.mapbox.com/android/maps/guides/styling-map/
    private val _mapBoxStyle = MutableLiveData<String>()
    val mapBoxStyle: LiveData<String> = _mapBoxStyle

    fun updateMapBoxStyle(style: String) {
        _mapBoxStyle.value = style
    }

    private val _miniPoi = MutableLiveData<Poi>()
    val poi: LiveData<Poi> = _miniPoi

    fun updateMiniPoi(poi: Poi) {
        _miniPoi.value = poi
    }

    //Whether or not the Mapbox LocationComponent is enabled or not
    private var locationComponentEnabled : Boolean = false

    fun setLocationComponentEnabled(value: Boolean) {
        locationComponentEnabled = value
    }

    fun getLocationComponentEnabled() : Boolean {
        return locationComponentEnabled
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<MapViewState>?> = when(stateEvent) {

            is MapStateEvent.GetPoiEvent -> {
                getPoi.execute(stateEvent)
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun handleNewData(data: MapViewState) {
        Log.d("MyDebug", "MapViewModel(handleNewData)")
    }

}
