package com.trkkr.trkkrclean.cache.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchItem(searchItem: SearchItemEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun prePopulate(searchItems: List<SearchItemEntity>)


    @Query("SELECT * FROM search_table ORDER BY id DESC LIMIT 10")
    suspend fun fetchAllSearchItems(): List<SearchItemEntity>?


    @Query("SELECT * FROM search_table WHERE name = :name AND layer = :layer")
    suspend fun getItem(name: String, layer: String): SearchItemEntity?


}