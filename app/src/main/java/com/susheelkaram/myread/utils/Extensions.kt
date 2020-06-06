package com.susheelkaram.trackky.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode
import java.util.*

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.shareText(content: String, title: String = "Share Product") {
    var sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }

    var shareIntent = Intent.createChooser(sendIntent, title)
    this.startActivity(shareIntent)
}

fun View.showSnackBar(
    message: String,
    actionText: String = "",
    action: View.OnClickListener? = null
) {
    var snackBar: Snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)

    if (action != null && actionText.isNotEmpty()) snackBar.setAction(actionText, action)

    snackBar.show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun Double.round(decimalPlaces: Int = 1): Double {
    return this.toBigDecimal().setScale(decimalPlaces, RoundingMode.UP).toDouble()
}

fun Float.round(decimalPlaces: Int = 1): Float {
    return this.toBigDecimal().setScale(decimalPlaces, RoundingMode.UP).toFloat()
}

fun Calendar.nowInSecs(): Long {
    return (this.timeInMillis / 1000)
}

//fun BottomNavigationView.selectItemByIndex(index: Int) {
//    if (this.menu.size() <= index) throw IndexOutOfBoundsException()
//    this.selectedItemId = this.menu.get(index).itemId
//}