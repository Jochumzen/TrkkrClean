package com.trkkr.trkkrclean.cache.search

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.trkkr.trkkrclean.cache.ioThread

@Database(entities = [SearchItemEntity::class], version = 1)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun searchDAO(): SearchDao

    companion object{

        @Volatile private var INSTANCE: SearchDatabase? = null

        fun getInstance(context: Context): SearchDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                SearchDatabase::class.java, "Sample.db")
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            getInstance(context).searchDAO().prePopulate(PREPOPULATE_SEARCH)
                        }
                    }
                })
                .build()

        val PREPOPULATE_SEARCH = listOf(
            SearchItemEntity(null,"55.70811", "13.197253", "101752279", "locality", "whosonfirst", "Lund", "centroid", "Sweden", "Scania", "Lund",
            "Lund", "Lunds Allhelgona", "Lund, Sweden", null, "Europe", "Feature", "13.1457503323", "55.678776639", "13.2553449291", "55.742051341"),
            SearchItemEntity(null, "56.030087", "12.724343", "101752277", "locality", "whosonfirst", "Helsingborg", "centroid", "Sweden", "Scania", "Helsingborg",
                "Helsingborg", "Helsingborgs Gustav Adolf", "Helsingborg, Sweden", null, "Europe", "Feature", "12.6616715", "55.9883195594", "12.7845542084", "56.0865096827")
        )



        val DATABASE_NAME = "search_table"
    }

}