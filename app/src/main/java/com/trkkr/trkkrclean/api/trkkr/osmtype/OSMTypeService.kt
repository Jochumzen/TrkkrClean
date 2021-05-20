package com.trkkr.trkkrclean.api.trkkr.osmtype
import retrofit2.http.GET

interface OSMTypeService {
    @GET("/api/osmtype/")
    suspend fun osmType(): OSMTypesDto
}