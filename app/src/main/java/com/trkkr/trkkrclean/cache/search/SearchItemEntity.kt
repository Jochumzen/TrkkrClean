package com.trkkr.trkkrclean.cache.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_table")
data class SearchItemEntity (

    @PrimaryKey
    var id: Int?,

    var coordinate1: String? = null,

    var coordinate2: String? = null,

    var OrsId: String? = null,

    var layer: String? = null,

    var source: String? = null,

    var name: String? = null,

    var accuracy: String? = null,

    var country: String? = null,

    var region: String? = null,

    var county: String? = null,

    var locality: String? = null,

    var localadmin: String? = null,

    var label: String? = null,

    var neighbourhood: String? = null,

    var continent: String? = null,

    var type: String? = null,

    var bbox1: String? = null,

    var bbox2: String? = null,

    var bbox3: String? = null,

    var bbox4: String? = null

    )