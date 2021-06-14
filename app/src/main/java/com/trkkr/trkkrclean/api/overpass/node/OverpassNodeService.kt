package com.trkkr.trkkrclean.api.overpass.node

import com.trkkr.trkkrclean.api.overpass.way.OverpassWayDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassNodeService {

    @GET("/api/interpreter")
    suspend fun getNode(
        @Query("data") data: String
    ): OverpassNodeDto

}