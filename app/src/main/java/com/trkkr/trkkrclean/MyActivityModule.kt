package com.trkkr.trkkrclean

import android.app.Application
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton


@Module
@InstallIn(ActivityComponent::class)
object MyActivityModule {

    @Provides
    fun provideTrkkrMapView() : TrkkrMapView {
        return TrkkrMapView()
    }

    @Provides
    fun provideTrkkrLocation(application: Application) : TrkkrLocation {
        val permissionsUtil = PermissionsUtil()
        val locationEngine = LocationEngineProvider.getBestLocationEngine(application)
        return TrkkrLocation(permissionsUtil, locationEngine)
    }
}
