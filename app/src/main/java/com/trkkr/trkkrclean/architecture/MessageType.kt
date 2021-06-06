package com.trkkr.trkkrclean.architecture

sealed class MessageType {
    class Success: MessageType()

    class Error: MessageType()

    class Info: MessageType()

    class None: MessageType()
}
