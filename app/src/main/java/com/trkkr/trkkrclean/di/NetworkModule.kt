package com.trkkr.trkkrclean.di

import com.trkkr.trkkrclean.api.ors.directions.DirectionsService
import com.trkkr.trkkrclean.api.ors.reverseSearch.ReverseSearchService
import com.trkkr.trkkrclean.api.ors.search.SearchService
import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypeService
import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesDataSource
import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesNetworkDataSource
import com.trkkr.trkkrclean.api.wikidata.WikidataService
import com.trkkr.trkkrclean.api.wikipedia.WikipediaService
import com.trkkr.trkkrclean.di.Constants.DIRECTIONS_URL
import com.trkkr.trkkrclean.di.Constants.OVERPASS_URL
import com.trkkr.trkkrclean.di.Constants.REVERSE_SEARCH_URL
import com.trkkr.trkkrclean.di.Constants.SEARCH_URL
import com.trkkr.trkkrclean.di.Constants.WIKIDATA_URL
import com.trkkr.trkkrclean.di.Constants.WIKIPEDIA_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideOSMTypesDataSource(): OSMTypesDataSource =
        OSMTypesNetworkDataSource(
            Retrofit
                .Builder()
                .baseUrl("http://jochumzen-001-site1.htempurl.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OSMTypeService::class.java)
        )

    @Singleton
    @Provides
    fun provideSearchService(): SearchService {
        return Retrofit.Builder()
            .baseUrl(SEARCH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchService::class.java)
    }

    @Singleton
    @Provides
    fun provideReverseSearchService(): ReverseSearchService {
        return Retrofit.Builder()
            .baseUrl(REVERSE_SEARCH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReverseSearchService::class.java)
    }

    @Singleton
    @Provides
    fun provideDirectionsService(): DirectionsService {
        return Retrofit.Builder()
            .baseUrl(DIRECTIONS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DirectionsService::class.java)
    }

    @Singleton
    @Provides
    fun provideOverpassNodeService(): OverpassNodeService {
        return Retrofit.Builder()
            .baseUrl(OVERPASS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OverpassNodeService::class.java)
    }

    @Singleton
    @Provides
    fun provideWikidataService(): WikidataService {
        return Retrofit.Builder()
            .baseUrl(WIKIDATA_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikidataService::class.java)
    }

    @Singleton
    @Provides
    fun provideWikipediaService(): WikipediaService {
        return Retrofit.Builder()
            .baseUrl(WIKIPEDIA_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikipediaService::class.java)
    }
}