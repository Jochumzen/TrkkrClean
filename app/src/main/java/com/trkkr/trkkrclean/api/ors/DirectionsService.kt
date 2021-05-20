package com.trkkr.trkkrclean.api.ors
import retrofit2.http.GET

interface DirectionsService {
    @GET()
    suspend fun directions() : DirectionsDto

    // "https://api.openrouteservice.org/v2/directions/foot-walking/?api_key={key}&start={start}&end={end}"
}