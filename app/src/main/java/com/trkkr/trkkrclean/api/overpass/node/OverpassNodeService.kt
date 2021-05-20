package com.trkkr.trkkrclean.api.overpass.node

import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassNodeService {
    @GET("/api/")
    suspend fun getNode(
        @Query("interpreter") data: String
    ): OverpassNodeDto
}