package com.trkkr.trkkrclean.presentation.search

import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.geometry.LatLng
import com.trkkr.trkkrclean.architecture.StateEvent

sealed class SearchStateEvent: StateEvent {
    class GetCachedSearchDataEvent(

    ): SearchStateEvent() {

        override fun errorInfo(): String {
            return "Error getting MiniPoi."
        }

        override fun eventName(): String {
            return "GetMiniPoiEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }
}