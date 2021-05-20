package com.trkkr.trkkrclean.api.trkkr
import retrofit2.http.GET

interface ImageService {
    @GET()
    suspend fun getImage(): ImageDto
}