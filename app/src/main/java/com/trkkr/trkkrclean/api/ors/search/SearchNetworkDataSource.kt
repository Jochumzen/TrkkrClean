package com.trkkr.trkkrclean.api.ors.search
import javax.inject.Inject

class SearchNetworkDataSource @Inject constructor(
    private val searchService: SearchService
) : SearchDataSource {
    override suspend fun fetchSearchResult() = searchService.search()
}