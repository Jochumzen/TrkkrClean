package com.trkkr.trkkrclean.API.OpenRouteService
import retrofit2.http.GET

interface SearchService {
    @GET("")
    suspend fun search(): SearchDto
}