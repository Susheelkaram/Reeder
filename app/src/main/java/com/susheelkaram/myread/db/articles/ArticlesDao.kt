package com.susheelkaram.myread.db.articles

import androidx.lifecycle.LiveData
import androidx.room.*
import com.susheelkaram.myread.utils.Constants

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Dao
interface ArticlesDao {
    @Query("SELECT * FROM ${Constants.TABLE_NAME_FEED_ARTICLES}")
    fun getAll() : LiveData<List<FeedArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg articles: FeedArticle) : List<Long>

    @Update
    suspend fun update(vararg articles: FeedArticle) : Int

    @Delete
    suspend fun delete(vararg articles: FeedArticle) : Int
}