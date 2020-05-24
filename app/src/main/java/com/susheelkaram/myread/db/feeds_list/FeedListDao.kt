package com.susheelkaram.myread.db.feeds_list

import androidx.lifecycle.LiveData
import androidx.room.*
import com.susheelkaram.myread.utils.Constants

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Dao
interface FeedListDao {
    @Query("SELECT * FROM ${Constants.TABLE_NAME_FEEDLIST}")
    fun getFeedList(): LiveData<List<Feed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeeds(vararg feeds: Feed): List<Long>

    @Delete
    suspend fun deleteFeeds(vararg feeds: Feed): Int

    @Update
    suspend fun updateFeeds(vararg feeds: Feed): Int
}