package com.trkkr.trkkrclean.api.overpass.node

import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassNodeService {
    @GET("/api/interpreter?data=[out:json];(node(6874973681););out meta;")
    suspend fun getNode(
        //@Query("interpreter") data: String
    ): OverpassNodeDto
}