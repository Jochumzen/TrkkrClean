package com.trkkr.trkkrclean.API.Trkkr
import retrofit2.http.GET

interface OSMTypeService {
    @GET()
    suspend fun OsmType(): OSMTypeDto
}