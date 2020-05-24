package com.susheelkaram.myread.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.susheelkaram.myread.db.feeds_list.Feed
import com.susheelkaram.myread.db.feeds_list.FeedListDao
import com.susheelkaram.myread.utils.Constants

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */

@Database(entities = arrayOf(Feed::class), version = 1, exportSchema = false)
abstract class DB : RoomDatabase() {
    abstract fun feedListDao(): FeedListDao

    companion object {
        @Volatile
        var INSTANCE: DB? = null

        fun getInstance(context: Context): DB {
            INSTANCE?.let {
                return it
            }

            synchronized(this) {
                var instance = Room.databaseBuilder(
                    context,
                    DB::class.java,
                    Constants.FILE_NAME_DB
                ).build()

                INSTANCE = instance
                return instance
            }
        }

    }
}
