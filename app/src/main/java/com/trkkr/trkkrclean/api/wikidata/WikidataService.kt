package com.trkkr.trkkrclean.api.wikidata
import retrofit2.http.GET

interface WikidataService {
    @GET("")
    suspend fun getWikidata(): WikidataDto
}