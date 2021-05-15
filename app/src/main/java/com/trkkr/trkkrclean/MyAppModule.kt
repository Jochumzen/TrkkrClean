package com.trkkr.trkkrclean

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object MyAppModule {

    @Provides
    fun provideTrkkrMapBox(): ITrkkrMapbox {
        return TrkkrMapbox()
    }
}

