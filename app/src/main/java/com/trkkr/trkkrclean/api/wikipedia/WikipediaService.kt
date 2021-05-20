package com.trkkr.trkkrclean.api.wikipedia
import retrofit2.http.GET

interface WikipediaService {
    @GET("")
    suspend fun getWikipedia(): WikipediaDto
}