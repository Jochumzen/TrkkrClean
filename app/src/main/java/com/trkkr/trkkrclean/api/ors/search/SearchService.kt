package com.trkkr.trkkrclean.api.ors.search
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/geocode/search/")
    suspend fun searchWithLatLon(
        @Query("api_key") key: String,
        @Query("text") text: String,
        @Query("focus.point.lon") lon: String,
        @Query("focus.point.lat") lat: String
    ): SearchDto

    suspend fun searchWithoutLatLon(
        @Query("api_key") key: String,
        @Query("text") text: String,
    ): SearchDto
}