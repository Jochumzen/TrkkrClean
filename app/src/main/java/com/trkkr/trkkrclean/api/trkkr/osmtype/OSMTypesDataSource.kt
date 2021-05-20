package com.trkkr.trkkrclean.api.trkkr.osmtype

interface OSMTypesDataSource {
    suspend fun fetchOsmTypes() : OSMTypesDto
}