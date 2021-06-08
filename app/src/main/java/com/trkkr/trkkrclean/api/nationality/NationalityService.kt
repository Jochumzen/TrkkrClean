package com.example.play71.network

import com.example.play71.network.NationalityDto
import retrofit2.http.GET

interface NationalityService {
    @GET("/api/nation/random_nation")
    suspend fun nationality() : NationalityDto
}