package com.trkkr.trkkrclean.di

import android.app.Application
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineProvider
import com.trkkr.trkkrclean.utilities.Mapper
import com.trkkr.trkkrclean.utilities.Toaster
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object MyActivityModule {



    @Provides
    fun providesToaster(application: Application): Toaster {
        return Toaster(application)
    }
    /*
    @Provides
    fun provideLocationEngine(application: Application): LocationEngine {
        return LocationEngineProvider.getBestLocationEngine(application)
    }


    @Provides
    fun providePermissionsUtil() : PermissionsUtil {
        return PermissionsUtil()
    }

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



    @Provides
    fun provideTrkkrPermission(application: Application) : TrkkrPermission {
        val permissionsUtil = PermissionsUtil()
        return TrkkrPermission(permissionsUtil)
    }

    @Provides
    fun provideTrkkrLocationComponent(application: Application) : TrkkrLocationComponent {
        val permissionsUtil = PermissionsUtil()
        val toaster = Toaster(application)
        return TrkkrLocationComponent(permissionsUtil, toaster)
    }

     */
}
