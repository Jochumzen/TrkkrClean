package com.trkkr.trkkrclean.api.overpass.way

import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassWayService {

    @GET("/api/interpreter")
    suspend fun getWay(
        @Query("data") data: String
    ): OverpassWayDto
}