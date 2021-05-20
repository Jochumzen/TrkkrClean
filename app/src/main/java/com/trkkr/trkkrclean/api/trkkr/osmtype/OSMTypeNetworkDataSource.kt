package com.trkkr.trkkrclean.api.trkkr.osmtype

import javax.inject.Inject

class OSMTypesNetworkDataSource @Inject constructor (
    private val osmTypeService: OSMTypeService
) : OSMTypesDataSource {
    override suspend fun fetchOsmTypes() = osmTypeService.OsmType()
}