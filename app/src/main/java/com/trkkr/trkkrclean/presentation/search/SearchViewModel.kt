package com.trkkr.trkkrclean.presentation.search

import com.mapbox.mapboxsdk.camera.CameraPosition
import com.trkkr.trkkrclean.architecture.DataState
import com.trkkr.trkkrclean.architecture.StateEvent
import com.trkkr.trkkrclean.interactors.GetCachedSearchData
import com.trkkr.trkkrclean.presentation.BaseViewModel
import com.trkkr.trkkrclean.presentation.map.MapStateEvent
import com.trkkr.trkkrclean.presentation.map.MapViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCachedSearchData: GetCachedSearchData
): BaseViewModel<SearchViewState>() {

    private var _cameraPosition: CameraPosition? = null

    fun setSearchCameraPosition(cameraPosition: CameraPosition?) {
        _cameraPosition = CameraPosition.Builder()
            .target(cameraPosition?.target)
            .zoom(
                cameraPosition?.zoom?.times(0.9) ?: 9.0
            )
            .tilt(20.0)
            .build()
    }

    fun getCameraPosition() : CameraPosition? {
        return _cameraPosition
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job: Flow<DataState<SearchViewState>?> = when(stateEvent) {

            is SearchStateEvent.GetCachedSearchDataEvent -> {
                getCachedSearchData.execute(stateEvent)
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun handleNewData(data: SearchViewState) {
        TODO("Not yet implemented")
    }
}