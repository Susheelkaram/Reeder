package com.susheelkaram.myread.utils

import androidx.appcompat.app.AppCompatDelegate

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class Utils {
    companion object {
        fun switchDarkLightTheme() {
            if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}