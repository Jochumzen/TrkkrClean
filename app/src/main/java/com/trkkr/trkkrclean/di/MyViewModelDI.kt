package com.trkkr.trkkrclean.di

import com.example.play71.network.NationalityDtoMapper
import com.example.play71.network.NationalityService
import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeDtoMapper
import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeService
import com.trkkr.trkkrclean.api.overpass.way.OverpassWayDtoMapper
import com.trkkr.trkkrclean.interactors.GetMiniPoi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MyViewModelDI {

    @Provides
    fun provideGetMiniPoi(
        overpassNodeService: OverpassNodeService,
        overpassNodeDtoMapper: OverpassNodeDtoMapper,
    ) : GetMiniPoi {
        return GetMiniPoi(overpassNodeService, overpassNodeDtoMapper)
    }

}