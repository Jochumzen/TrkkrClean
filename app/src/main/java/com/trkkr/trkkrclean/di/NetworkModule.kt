package com.trkkr.trkkrclean.di

import com.trkkr.trkkrclean.api.ors.directions.DirectionsService
import com.trkkr.trkkrclean.api.ors.reverseSearch.ReverseSearchService
import com.trkkr.trkkrclean.api.ors.search.SearchService
import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeDtoMapper
import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeService
import com.trkkr.trkkrclean.api.overpass.way.OverpassWayDtoMapper
import com.trkkr.trkkrclean.api.overpass.way.OverpassWayService
import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesService
import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesDataSource
import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesNetworkDataSource
import com.trkkr.trkkrclean.api.wikidata.WikidataService
import com.trkkr.trkkrclean.api.wikipedia.WikipediaService
import com.trkkr.trkkrclean.di.URLs.DIRECTIONS_URL
import com.trkkr.trkkrclean.di.URLs.OVERPASS_URL
import com.trkkr.trkkrclean.di.URLs.REVERSE_SEARCH_URL
import com.trkkr.trkkrclean.di.URLs.SEARCH_URL
import com.trkkr.trkkrclean.di.URLs.WIKIDATA_URL
import com.trkkr.trkkrclean.di.URLs.WIKIPEDIA_URL
import com.trkkr.trkkrclean.utilities.Mapper
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

    @Singleton
    @Provides
    fun provideMapper() : Mapper {
        return Mapper()
    }

    @Singleton
    @Provides
    fun provideOSMTypesDataSource(): OSMTypesDataSource =
        OSMTypesNetworkDataSource(
            Retrofit
                .Builder()
                .baseUrl("http://jochumzen-001-site1.htempurl.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OSMTypesService::class.java)
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
    fun provideOverpassNodeDtoMapper() : OverpassNodeDtoMapper {
        return OverpassNodeDtoMapper()
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
    fun provideOverpassWayDtoMapper() : OverpassWayDtoMapper {
        return OverpassWayDtoMapper()
    }

    @Singleton
    @Provides
    fun provideOverpassWayService(): OverpassWayService {
        return Retrofit.Builder()
            .baseUrl(OVERPASS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OverpassWayService::class.java)
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