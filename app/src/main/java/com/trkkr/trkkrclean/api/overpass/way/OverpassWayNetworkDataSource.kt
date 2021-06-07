package com.trkkr.trkkrclean.api.overpass.way


import javax.inject.Inject

class OverpassWayNetworkDataSource @Inject constructor (
    private val overpassWayService: OverpassWayService
) : OverpassWayDataSource {
    override suspend fun fetchWay(data: String) = overpassWayService.getWay(data)
}