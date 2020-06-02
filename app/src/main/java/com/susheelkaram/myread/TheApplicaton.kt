package com.susheelkaram.myread

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.susheelkaram.myread.utils.Utils

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class TheApplicaton : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}