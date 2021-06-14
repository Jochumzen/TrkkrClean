package com.trkkr.trkkrclean.di

import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeDtoMapper
import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeService
import com.trkkr.trkkrclean.api.overpass.way.OverpassWayDtoMapper
import com.trkkr.trkkrclean.api.overpass.way.OverpassWayService
import com.trkkr.trkkrclean.cache.search.SearchDao
import com.trkkr.trkkrclean.interactors.GetCachedSearchData
import com.trkkr.trkkrclean.interactors.GetPoi
import com.trkkr.trkkrclean.utilities.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MyViewModelDI {

    @Provides
    fun provideGetPoi(
        overpassNodeService: OverpassNodeService,
        overpassNodeDtoMapper: OverpassNodeDtoMapper,
        overpassWayService: OverpassWayService,
        overpassWayDtoMapper: OverpassWayDtoMapper,
        mapper: Mapper
    ) : GetPoi {
        return GetPoi(overpassNodeService, overpassNodeDtoMapper, overpassWayService, overpassWayDtoMapper, mapper)
    }

    @Provides
    fun provideGetCachedSearchData(
        searchDao: SearchDao
    ) : GetCachedSearchData {
        return GetCachedSearchData(searchDao)
    }

}