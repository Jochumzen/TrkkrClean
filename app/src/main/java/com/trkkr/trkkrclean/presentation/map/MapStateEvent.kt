package com.trkkr.trkkrclean.presentation.map

import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.geometry.LatLng
import com.trkkr.trkkrclean.architecture.StateEvent

sealed class MapStateEvent: StateEvent {
    class GetPoiEvent(
        val latLng: LatLng,
        val feature: Feature,
        val userLocation: LatLng?
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
