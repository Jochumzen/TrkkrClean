package com.trkkr.trkkrclean.api.ors.directions
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsService {
    @GET("/v2/directions/foot-walking/")
    suspend fun directions(

        @Query("api_key") key: String,
        @Query("start") start: String,
        @Query("end") end: String

    ) : DirectionsDto

}