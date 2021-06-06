package com.trkkr.trkkrclean.architecture

sealed class UIComponentType {
    class Toast: UIComponentType()

    class Dialog: UIComponentType()

    class None: UIComponentType()
}