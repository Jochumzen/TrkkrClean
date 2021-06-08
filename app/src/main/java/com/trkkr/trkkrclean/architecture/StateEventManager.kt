package com.trkkr.trkkrclean.architecture

import androidx.lifecycle.MutableLiveData

class StateEventManager {

    private val activeStateEvents: HashMap<String, StateEvent> = HashMap()

    private val _shouldDisplayProgressBar: MutableLiveData<Boolean> = MutableLiveData()

    fun isStateEventActive(stateEvent: StateEvent): Boolean{
        //printLogD("DCM sem", "is state event active? " +
        //        "${activeStateEvents.containsKey(stateEvent.eventName())}")
        return activeStateEvents.containsKey(stateEvent.eventName())
    }

    fun addStateEvent(stateEvent: StateEvent){
        //EspressoIdlingResource.increment()
        activeStateEvents.put(stateEvent.eventName(), stateEvent)
        syncNumActiveStateEvents()
    }

    private fun syncNumActiveStateEvents(){
        var shouldDisplayProgressBar = false
        for(stateEvent in activeStateEvents.values){
            if(stateEvent.shouldDisplayProgressBar()){
                shouldDisplayProgressBar = true
            }
        }
        _shouldDisplayProgressBar.value = shouldDisplayProgressBar
    }

    fun removeStateEvent(stateEvent: StateEvent?){
        //printLogD("DCM sem", "remove state event: ${stateEvent?.eventName()}")
        stateEvent?.let {
            //EspressoIdlingResource.decrement()
        }
        activeStateEvents.remove(stateEvent?.eventName())
        syncNumActiveStateEvents()
    }
}