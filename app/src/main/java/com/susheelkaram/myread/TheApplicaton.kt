package com.susheelkaram.myread

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.susheelkaram.myread.di.dbModule
import com.susheelkaram.myread.utils.ThemeUtil
import com.susheelkaram.myread.utils.Utils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class TheApplicaton : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeUtil.setUserSelectedTheme(this)

        startKoin {
            androidContext(this@TheApplicaton)
            androidLogger(Level.ERROR)
            modules(dbModule)
        }
    }
}