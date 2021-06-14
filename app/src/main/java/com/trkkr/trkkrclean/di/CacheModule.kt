package com.trkkr.trkkrclean.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.trkkr.trkkrclean.cache.ioThread
import com.trkkr.trkkrclean.cache.search.SearchDao
import com.trkkr.trkkrclean.cache.search.SearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideSearchDB(app: Application, searchDaoProvider: Provider<SearchDao>) : SearchDatabase {
        //return SearchDatabase.getInstance(app)

        return Room
            .databaseBuilder(app, SearchDatabase::class.java, SearchDatabase.DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // insert the data on the IO Thread
                    ioThread {
                        searchDaoProvider.get().prePopulate(SearchDatabase.PREPOPULATE_SEARCH)
                    }
                }
            })
            //.fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchDao(db: SearchDatabase) : SearchDao {
        return db.searchDAO()
    }
}