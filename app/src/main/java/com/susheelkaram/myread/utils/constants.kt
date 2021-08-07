package com.susheelkaram.myread.utils

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
object Constants {
    const val FILE_NAME_DB = "my_read_db"
    const val TABLE_NAME_FEEDLIST = "table_feed_list"
    const val TABLE_NAME_FEED_ARTICLES = "table_feed_articles"

    // Articles table
    const val COL_IS_BOOKMARKED = "isBookmarked"
    const val COL_PUB_DATE = "pubDate"
    const val COL_IS_READ = "isRead"
    const val COL_FEED_ID = "feedId"

    // Feed Syncing
    const val SYNC_INTERVAL = 30L // mins
    const val FEED_SYNC_WORK_NAME = "feedSyncWork"

    // Preferences
    const val PREF_FILE_COMMON = "commonPreferences"
    const val PREF_THEME_MODE = "themeMode"
}