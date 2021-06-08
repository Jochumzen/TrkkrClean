package com.trkkr.trkkrclean.interactors

import com.example.play71.network.NationalityDtoMapper
import com.example.play71.network.NationalityService
import com.trkkr.trkkrclean.api.ApiResult
import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeDtoMapper
import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeService
import com.trkkr.trkkrclean.api.overpass.way.OverpassWayDtoMapper
import com.trkkr.trkkrclean.architecture.*
import com.trkkr.trkkrclean.domain.OsmType
import com.trkkr.trkkrclean.presentation.MapStateEvent
import com.trkkr.trkkrclean.presentation.MapViewState
import com.trkkr.trkkrclean.utilities.getOSMId
import com.trkkr.trkkrclean.utilities.getOSMType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMiniPoi(
    private val overpassNodeService: OverpassNodeService,
    private val overpassNodeDtoMapper : OverpassNodeDtoMapper,
    private val nationalityService : NationalityService,
    private val nationalityDtoMapper: NationalityDtoMapper,
    //private val overpassWayDtoMapper : OverpassWayDtoMapper
) {


    fun execute(
        stateEvent: StateEvent
    ) : Flow<DataState<MapViewState>?> = flow {

        val osmType = (stateEvent as MapStateEvent.GetMiniPoiEvent).feature.getOSMType()
        val osmId = (stateEvent as MapStateEvent.GetMiniPoiEvent).feature.getOSMId()

        if(osmType == OsmType.NODE)
        {
            val networkResult = safeApiCall(Dispatchers.IO) {
                val overpassCallString = "data=[out:json];(node($osmId););out meta;"
                overpassNodeDtoMapper.mapToDomainModel(overpassNodeService.getNode())
            }

            println("MyDebug: result: $networkResult")

            if(networkResult is ApiResult.Success) {

                val viewState = MapViewState(shit = "abc")

                val dataState = DataState.data<MapViewState>(
                    Response(
                        message = "Getting osm object from network success",
                        uiComponentType = UIComponentType.Toast(),
                        messageType = MessageType.Success()
                    ), data = viewState, stateEvent)
                emit(dataState)

            } else {
                val dataState = DataState.error<MapViewState>(
                    Response(
                        message = "Getting osm object from network failed",
                        uiComponentType = UIComponentType.Toast(),
                        messageType = MessageType.Error()
                    ), stateEvent)
                emit(dataState)
            }
        }

    }


}