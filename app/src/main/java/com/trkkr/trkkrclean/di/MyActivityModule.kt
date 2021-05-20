package com.trkkr.trkkrclean.di

import android.app.Application
import com.mapbox.android.core.location.LocationEngineProvider
import com.trkkr.trkkrclean.Mapper
import com.trkkr.trkkrclean.PermissionsUtil
import com.trkkr.trkkrclean.TrkkrLocation
import com.trkkr.trkkrclean.TrkkrMapView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object MyActivityModule {

    @Provides
    fun provideTrkkrMapView() : TrkkrMapView {
        return TrkkrMapView(Mapper())
    }

    @Provides
    fun provideTrkkrLocation(application: Application) : TrkkrLocation {
        val permissionsUtil = PermissionsUtil()
        val locationEngine = LocationEngineProvider.getBestLocationEngine(application)
        return TrkkrLocation(permissionsUtil, locationEngine)
    }
}
