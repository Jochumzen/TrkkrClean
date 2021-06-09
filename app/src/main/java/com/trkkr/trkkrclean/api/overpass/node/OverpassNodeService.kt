package com.trkkr.trkkrclean.api.overpass.node

import retrofit2.http.GET
import retrofit2.http.Query

interface OverpassNodeService {
    /*
        @GET("/api/interpreter?data=[out:json];(node(6874973681););out meta;")
    suspend fun getNode(
        @Query("interpreter") data: String
    ): OverpassNodeDto

     */

    @GET("/api/interpreter")
    suspend fun getNode(
        @Query("data") data: String
    ): OverpassNodeDto

}