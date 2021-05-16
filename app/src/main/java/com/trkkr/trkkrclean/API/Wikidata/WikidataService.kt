package com.trkkr.trkkrclean.API.Wikidata
import retrofit2.http.GET

interface WikidataService {
    @GET("")
    suspend fun getWikidata(): WikidataDto
}