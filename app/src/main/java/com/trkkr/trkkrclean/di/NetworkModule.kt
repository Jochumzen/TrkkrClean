package com.trkkr.trkkrclean.di

import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesService
import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesDataSource
import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                .create(OSMTypesService::class.java)
        )
}