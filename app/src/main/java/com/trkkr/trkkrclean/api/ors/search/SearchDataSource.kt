package com.trkkr.trkkrclean.api.ors.search

interface SearchDataSource {
    suspend fun fetchSearchResult(api_key: String, text: String, lon: String? = null, lat: String? = null): SearchDto
}