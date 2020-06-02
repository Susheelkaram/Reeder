package com.susheelkaram.myread.utils

import androidx.appcompat.app.AppCompatDelegate

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class Utils {
    companion object {
        fun switchDarkLightTheme() {
            if(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO) {
                setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        fun setThemeMode(mode: Int) {
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }
}