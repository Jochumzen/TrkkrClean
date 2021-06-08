package com.trkkr.trkkrclean.api.overpass.node

import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesDataSource
import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesService
import javax.inject.Inject

class OverpassNodeNetworkDataSource @Inject constructor (
    private val overpassNodeService: OverpassNodeService
) : OverpassNodeDataSource {
    //override suspend fun fetchNode(data: String) = overpassNodeService.getNode(data)
    override suspend fun fetchNode() = overpassNodeService.getNode()
}