package com.trkkr.trkkrclean.architecture

class MessageStack: ArrayList<StateMessage>() {

    fun isStackEmpty(): Boolean{
        return size == 0
    }

}