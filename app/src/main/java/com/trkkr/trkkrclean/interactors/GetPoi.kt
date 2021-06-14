package com.trkkr.trkkrclean.interactors

import android.util.Log
import com.trkkr.trkkrclean.api.ApiResult
import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeDtoMapper
import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeService
import com.trkkr.trkkrclean.api.overpass.way.OverpassWayDtoMapper
import com.trkkr.trkkrclean.api.overpass.way.OverpassWayService
import com.trkkr.trkkrclean.architecture.*
import com.trkkr.trkkrclean.cache.CacheResult
import com.trkkr.trkkrclean.cache.search.SearchDao
import com.trkkr.trkkrclean.domain.OsmType
import com.trkkr.trkkrclean.domain.Poi
import com.trkkr.trkkrclean.presentation.map.MapStateEvent
import com.trkkr.trkkrclean.presentation.map.MapViewState
import com.trkkr.trkkrclean.utilities.Mapper
import com.trkkr.trkkrclean.utilities.getOSMId
import com.trkkr.trkkrclean.utilities.getOSMType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPoi @Inject constructor(
    private val overpassNodeService: OverpassNodeService,
    private val overpassNodeDtoMapper : OverpassNodeDtoMapper,
    private val overpassWayService: OverpassWayService,
    private val overpassWayDtoMapper : OverpassWayDtoMapper,
    private val mapper: Mapper
) {

    fun execute(
        stateEvent: StateEvent
    ) : Flow<DataState<MapViewState>?> = flow {

        val osmType = (stateEvent as MapStateEvent.GetPoiEvent).feature.getOSMType()

        val distanceBetweenPoiAndUser = mapper.distance(stateEvent.userLocation, stateEvent.latLng)

        //Initialize a Poi with info we already have from the StateEvent
        val poi = Poi(feature = stateEvent.feature, mapClicked = stateEvent.latLng, userLocation = stateEvent.userLocation, osmType = osmType, distance = distanceBetweenPoiAndUser)

        val osmId = stateEvent.feature.getOSMId()

        //Poi is a Node
        if(osmType == OsmType.NODE)
        {
            Log.d("MyDebug", "GetPoi(execute) - found node.")

            val networkResult = safeApiCall(Dispatchers.IO) {
                overpassNodeDtoMapper.mapToDomainModel(overpassNodeService.getNode("[out:json];(node($osmId););out meta;"))
            }

            if(networkResult is ApiResult.Success) {

                if(networkResult.value?.isNotEmpty() == true) {

                    Log.d("MyDebug", "GetPoi(execute). networkResult Success : ${networkResult.value[0].type}, ${networkResult.value[0].id}, ${networkResult.value[0].lat}, ${networkResult.value[0].lon}")

                    poi.osmNode = networkResult.value[0]

                    val viewState = MapViewState(poi = poi)

                    val dataState = DataState.data(
                        Response(
                            message = "Getting osm object from network success",
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Success()
                        ), data = viewState, stateEvent)

                    emit(dataState)

                } else {

                    Log.d("MyDebug", "GetPoi(execute). networkResult Success but no node")
                    val dataState = DataState.error<MapViewState>(
                        Response(
                            message = "Network success but no node",
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Error()
                        ), stateEvent)

                    emit(dataState)

                }
            } else {

                Log.d("MyDebug", "GetPoi(execute). networkResult Failed")
                val dataState = DataState.error<MapViewState>(
                    Response(
                        message = "Getting osm object from network failed",
                        uiComponentType = UIComponentType.Toast(),
                        messageType = MessageType.Error()
                    ), stateEvent)

                emit(dataState)
            }

        //Poi is a Way
        } else if(osmType == OsmType.WAY) {
            Log.d("MyDebug", "GetPoi(execute) - found way. Distance: $distanceBetweenPoiAndUser")

            val networkResult = safeApiCall(Dispatchers.IO) {
                overpassWayDtoMapper.mapToDomainModel(overpassWayService.getWay("[out:json];(way($osmId););out meta;"))
            }

            if(networkResult is ApiResult.Success) {

                if(networkResult.value?.isNotEmpty() == true) {

                    Log.d("MyDebug", "GetPoi(execute). networkResult Success : ${networkResult.value[0].type}, ${networkResult.value[0].id}")

                    poi.osmWay = networkResult.value[0]
                    val viewState = MapViewState(poi = poi)

                    val dataState = DataState.data<MapViewState>(
                        Response(
                            message = "Getting osm object from network success",
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Success()
                        ), data = viewState, stateEvent)

                    emit(dataState)

                } else {

                    Log.d("MyDebug", "GetPoi(execute). networkResult Success but no way")
                    val dataState = DataState.error<MapViewState>(
                        Response(
                            message = "Network success but no way",
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Error()
                        ), stateEvent)
                    emit(dataState)

                }

            } else {

                Log.d("MyDebug", "GetPoi(execute). networkResult Failed")
                val dataState = DataState.error<MapViewState>(
                    Response(
                        message = "Getting osm object from network failed",
                        uiComponentType = UIComponentType.Toast(),
                        messageType = MessageType.Error()
                    ), stateEvent)
                emit(dataState)
            }

        //Poi is a Relation - Not handled at the moment
        } else {
            Log.d("MyDebug", "GetPoi(execute). OSM Type is neither node nor way - not handled")
            val dataState = DataState.error<MapViewState>(
                Response(
                    message = "OSM Type is neither node nor way - not handled",
                    uiComponentType = UIComponentType.Toast(),
                    messageType = MessageType.Error()
                ), stateEvent)
            emit(dataState)
        }


    }


}