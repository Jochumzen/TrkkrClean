package com.trkkr.trkkrclean.API.OpenRouteService
import retrofit2.http.GET

interface ReverseSearchService {
    @GET("") // "https://api.openrouteservice.org/geocode/reverse?api_key={key}&point.lon={lon}&point.lat={lat}"
    suspend fun handleReverseSearch(): SearchDto
}