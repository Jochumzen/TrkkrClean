package com.trkkr.trkkrclean.API.Wikipedia
import retrofit2.http.GET

interface WikipediaService {
    @GET("")
    suspend fun getWikipedia(): WikipediaDto
}