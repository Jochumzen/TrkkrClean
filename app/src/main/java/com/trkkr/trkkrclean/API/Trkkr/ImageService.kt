package com.trkkr.trkkrclean.API.Trkkr
import retrofit2.http.GET

interface ImageService {
    @GET()
    suspend fun getImage(): ImageDto
}