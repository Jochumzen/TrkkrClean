package com.trkkr.trkkrclean.presentation

import androidx.lifecycle.ViewModel
import com.trkkr.trkkrclean.architecture.*
import kotlinx.coroutines.flow.flow

abstract class BaseViewModel<ViewState> : ViewModel() {

    fun emitInvalidStateEvent(stateEvent: StateEvent) = flow {
        emit(
            DataState.error<ViewState>(
                response = Response(
                    message = GenericErrors.INVALID_STATE_EVENT,
                    uiComponentType = UIComponentType.None(),
                    messageType = MessageType.Error()
                ),
                stateEvent = stateEvent
            )
        )
    }
}