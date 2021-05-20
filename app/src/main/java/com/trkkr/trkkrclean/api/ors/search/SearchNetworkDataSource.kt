package com.trkkr.trkkrclean.api.ors.search

import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypeService
import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesDataSource
import javax.inject.Inject

class SearchNetworkDataSource @Inject constructor(
    private val searchService: SearchService
) : SearchDataSource {
    override suspend fun fetchSearchResult() = searchService.search()
}