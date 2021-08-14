package com.susheelkaram.myread.ui.model

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
sealed class UiEvent {
    sealed class Navigate() : UiEvent() {
        data class Implicit(val action: String, val data: String) : Navigate() {}
        data class Explicit(val destination: Class<Any>) : Navigate()
    }
}