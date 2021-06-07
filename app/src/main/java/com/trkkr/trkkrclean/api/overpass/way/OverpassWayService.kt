package com.trkkr.trkkrclean.api.overpass.way

import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassWayService {
    @GET("/api/")
    suspend fun getWay(
        @Query("interpreter") data: String
    ): OverpassWayDto
}