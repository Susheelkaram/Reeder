package com.susheelkaram.myread.utils

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.susheelkaram.myread.R

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class ThemeUtil {
    companion object {
        fun switchThemeAndSetMenuIcon(app: Application, menuItem: MenuItem) {
            val switchedTheme = switchTheme(app)
            menuItem.setIcon(getThemeIcon(switchedTheme))
        }

        fun getThemeIcon(selectedTheme: Int): Int {
            return if (selectedTheme == AppCompatDelegate.MODE_NIGHT_NO) R.drawable.ic_baseline_wb_sunny_24
            else R.drawable.ic_brightness_2_black_24dp
        }

        fun switchTheme(app: Application) : Int{
            val themeToSwitchTo: Int = if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else AppCompatDelegate.MODE_NIGHT_YES
            setThemeMode(themeToSwitchTo)
            saveThemeSelection(themeToSwitchTo, app)
            return themeToSwitchTo
        }

        private fun setThemeMode(selectedTheme: Int) {
            AppCompatDelegate.setDefaultNightMode(selectedTheme)
        }

        fun setUserSelectedTheme(app: Application) {
            val selectedTheme = getSelectedThemeMode(app)
            setThemeMode(selectedTheme)
        }

        private fun saveThemeSelection(mode: Int, app: Application) {
            val preferences = app.getSharedPreferences(Constants.PREF_FILE_COMMON, Context.MODE_PRIVATE)
            with(preferences.edit()) {
                putInt(Constants.PREF_THEME_MODE, mode)
                apply()
            }
        }

        private fun getSelectedThemeMode(app: Application) : Int{
            val preferences = app.getSharedPreferences(Constants.PREF_FILE_COMMON, Context.MODE_PRIVATE)
            return preferences.getInt(Constants.PREF_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}