package com.trkkr.trkkrclean.presentation

import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.geometry.LatLng
import com.trkkr.trkkrclean.architecture.StateEvent

sealed class MapStateEvent: StateEvent {
    class GetMiniPoiEvent(
        val latLng: LatLng,
        val feature: Feature
    ): MapStateEvent() {

        override fun errorInfo(): String {
            return "Error getting MiniPoi."
        }

        override fun eventName(): String {
            return "GetMiniPoiEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }
}
