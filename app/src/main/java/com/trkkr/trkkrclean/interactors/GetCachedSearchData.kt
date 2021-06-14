package com.trkkr.trkkrclean.interactors

import com.trkkr.trkkrclean.architecture.DataState
import com.trkkr.trkkrclean.architecture.StateEvent
import com.trkkr.trkkrclean.architecture.safeCacheCall
import com.trkkr.trkkrclean.cache.search.SearchDao
import com.trkkr.trkkrclean.presentation.map.MapViewState
import com.trkkr.trkkrclean.presentation.search.SearchViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCachedSearchData @Inject constructor (
    private val searchDao: SearchDao,
    ) {

    fun execute(
        stateEvent: StateEvent
    ) : Flow<DataState<SearchViewState>?> = flow {

        val cacheResult = safeCacheCall(Dispatchers.IO){
            searchDao.fetchAllSearchItems()
        }

    }
}