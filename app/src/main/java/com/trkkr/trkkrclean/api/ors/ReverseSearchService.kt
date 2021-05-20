package com.trkkr.trkkrclean.api.ors
import retrofit2.http.GET

interface ReverseSearchService {
    @GET("/geocode/reverse/") // "https://api.openrouteservice.org/geocode/reverse?api_key={key}&point.lon={lon}&point.lat={lat}"
    suspend fun handleReverseSearch(): SearchDto
}