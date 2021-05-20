package com.trkkr.trkkrclean.api.trkkr.osmtype
import retrofit2.http.GET

interface OSMTypesService {
    @GET("/api/osmtype/")
    suspend fun OsmType(): OSMTypesDto
}