package com.trkkr.trkkrclean.api.ors.search
import javax.inject.Inject

class SearchNetworkDataSource @Inject constructor(private val searchService: SearchService) : SearchDataSource {

    override suspend fun fetchSearchResult(api_key: String, text: String, lon: String?, lat: String?): SearchDto {
        return if (lon == null || lat == null) {
            searchService.searchWithoutLatLon(api_key, text)
        } else {
            searchService.searchWithLatLon(api_key, text, lon, lat)
        }
    }

}