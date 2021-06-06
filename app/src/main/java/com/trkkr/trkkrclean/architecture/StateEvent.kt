package com.trkkr.trkkrclean.architecture

interface StateEvent {
    fun errorInfo(): String

    fun eventName(): String

    fun shouldDisplayProgressBar(): Boolean
}