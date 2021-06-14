package com.trkkr.trkkrclean.architecture

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

abstract class DataChannelManager<ViewState> {


    private var channelScope: CoroutineScope? = null

    private val stateEventManager: StateEventManager = StateEventManager()

    val messageStack = MessageStack()

    abstract fun handleNewData(data: ViewState)

    fun launchJob(
        stateEvent: StateEvent,
        jobFunction: Flow<DataState<ViewState>?>
    ) {

        //println("MyDebug: Inside launchJob of the DataChannelManager")
        if(canExecuteNewStateEvent(stateEvent)) {
            //println("MyDebug: StateEvent can be executed")
            addStateEvent(stateEvent)
            jobFunction
                .onEach { dataState ->
                    dataState?.let { dState ->
                        withContext(Dispatchers.Main) {
                            dataState.data?.let { data ->
                                handleNewData(data)
                            }
                            dataState.stateMessage?.let { stateMessage ->
                                handleNewStateMessage(stateMessage)
                            }
                            dataState.stateEvent?.let { stateEvent ->
                                removeStateEvent(stateEvent)
                            }
                        }
                    }
                }
                .launchIn(getChannelScope())
        }
    }

    private fun canExecuteNewStateEvent(stateEvent: StateEvent): Boolean{
        // If a job is already active, do not allow duplication
        if(isJobAlreadyActive(stateEvent)){
            return false
        }
        // Check the top of the stack, if a dialog is showing, do not allow new StateEvents
        if(!isMessageStackEmpty()){
            if(messageStack[0].response.uiComponentType == UIComponentType.Dialog()){
                return false
            }
        }
        return true
    }

    fun addStateEvent(stateEvent: StateEvent)
            = stateEventManager.addStateEvent(stateEvent)

    fun isJobAlreadyActive(stateEvent: StateEvent): Boolean {
        return isStateEventActive(stateEvent)
    }

    private fun isStateEventActive(stateEvent: StateEvent)
            = stateEventManager.isStateEventActive(stateEvent)

    fun isMessageStackEmpty(): Boolean {
        return messageStack.isStackEmpty()
    }

    private fun handleNewStateMessage(stateMessage: StateMessage){
        appendStateMessage(stateMessage)
    }

    private fun appendStateMessage(stateMessage: StateMessage) {
        messageStack.add(stateMessage)
    }

    fun removeStateEvent(stateEvent: StateEvent?)
            = stateEventManager.removeStateEvent(stateEvent)

    fun getChannelScope(): CoroutineScope {
        return channelScope?: setupNewChannelScope(CoroutineScope(Dispatchers.IO))
    }

    private fun setupNewChannelScope(coroutineScope: CoroutineScope): CoroutineScope {
        channelScope = coroutineScope
        return channelScope as CoroutineScope
    }
}