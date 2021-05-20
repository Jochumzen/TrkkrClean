package com.trkkr.trkkrclean.api.ors.search

interface SearchDataSource {
    suspend fun fetchSearchResult(): SearchDto
}