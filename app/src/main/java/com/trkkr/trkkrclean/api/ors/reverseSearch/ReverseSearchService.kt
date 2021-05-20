package com.trkkr.trkkrclean.api.ors.reverseSearch
import com.trkkr.trkkrclean.api.ors.search.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ReverseSearchService {
    @GET("/geocode/reverse/")
    suspend fun handleReverseSearch(

        @Query("api_key") key: String,
        @Query("point.lon") lon: String,
        @Query("point.lat") lat: String

    ): SearchDto
}