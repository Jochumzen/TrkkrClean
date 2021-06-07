package com.trkkr.trkkrclean.api.overpass.way

import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeDto

interface OverpassWayDataSource {
    suspend fun fetchWay(data: String) : OverpassWayDto
}