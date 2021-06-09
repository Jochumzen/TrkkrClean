package com.trkkr.trkkrclean.api.overpass.node

import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesDto

interface OverpassNodeDataSource {
    suspend fun fetchNode(data: String) : OverpassNodeDto
    //suspend fun fetchNode() : OverpassNodeDto
}