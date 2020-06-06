package com.susheelkaram.myread.utils

import androidx.appcompat.app.AppCompatDelegate
import java.text.SimpleDateFormat
import java.util.*

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

        fun getEpochFromTimestamp(timeStamp: String?, inMillis: Boolean = false): Long {
            if(timeStamp.isNullOrEmpty()) return 0

            var rssTimeFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z")
            var dateTime = rssTimeFormat.parse(timeStamp)
            return if (inMillis) dateTime.time else (dateTime.time / 1000)
        }

        fun formatTimeStamp(timeStamp: String) : String{
            var epoch = getEpochFromTimestamp(timeStamp)
            return getFormattedTimeStamp(epoch)
        }

        fun getFormattedTimeStamp(epoch: Long, isMillis: Boolean = false) : String {
            var secs = epoch
            if(!isMillis) secs *= 1000

            val dateTime: Date = Date(secs)
            val formatTime: SimpleDateFormat = SimpleDateFormat("h:mm a, ")
            val formatDay: SimpleDateFormat = SimpleDateFormat("EEE, d MMM YYYY")
            var todayOrYesterdayFormat: String? = getYesterdayOrTodayFormat(dateTime);

            val time: String = formatTime.format(dateTime)
            val day: String = formatDay.format(dateTime)

            if(todayOrYesterdayFormat == null) return time + day
            else return time + todayOrYesterdayFormat
        }

        private fun getYesterdayOrTodayFormat(dateTime: Date) : String? {
            var cal : Calendar = Calendar.getInstance()
            var calToday : Calendar = Calendar.getInstance()

            cal.time = dateTime

            val day: Int = cal.get(Calendar.DAY_OF_MONTH)
            val month: Int = cal.get(Calendar.MONTH)
            val year: Int = cal.get(Calendar.YEAR)

            if(calToday.get(Calendar.YEAR) == year && calToday.get(Calendar.MONTH) == month){
                return when(day){
                    calToday.get(Calendar.DAY_OF_MONTH) ->  "Today"
                    calToday.get(Calendar.DAY_OF_MONTH) - 1 -> "Yesterday"
                    else -> null
                } as String?
            }

            return null
        }
    }
}