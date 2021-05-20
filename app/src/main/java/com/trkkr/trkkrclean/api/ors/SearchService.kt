package com.trkkr.trkkrclean.api.ors
import retrofit2.http.GET

interface SearchService {
    @GET("")
    suspend fun search(): SearchDto
}