package com.susheelkaram.myread.callbacks

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
interface RecyclerViewCallback<T> {
    fun onItemClick(type: String = "item", item: T)
}

fun <T> RecyclerViewCallBack(lamda: (type: String, item: T) -> Unit): RecyclerViewCallback<T> {
    var callback = object : RecyclerViewCallback<T> {
        override fun onItemClick(type: String, item: T) {
           lamda(type, item)
        }
    }
    return callback
}