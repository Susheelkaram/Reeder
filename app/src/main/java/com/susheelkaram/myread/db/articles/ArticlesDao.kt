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
    @Query("SELECT * FROM ${Constants.TABLE_NAME_FEED_ARTICLES} ORDER BY ${Constants.COL_PUB_DATE} DESC")
    fun getAll() : LiveData<List<FeedArticle>>

    @Query("SELECT * FROM ${Constants.TABLE_NAME_FEED_ARTICLES} ORDER BY ${Constants.COL_PUB_DATE} DESC")
    suspend fun getAllNow() : List<FeedArticle>

    @Query("SELECT * FROM ${Constants.TABLE_NAME_FEED_ARTICLES} WHERE ${Constants.COL_FEED_ID} = :feedId")
    suspend fun getArticlesByFeedIdSynchronously(feedId: Long) : List<FeedArticle>

    @Query("SELECT * FROM ${Constants.TABLE_NAME_FEED_ARTICLES} WHERE ${Constants.COL_IS_BOOKMARKED} = 1 ORDER BY ${Constants.COL_PUB_DATE} DESC")
    fun getBookmarkedArticles() : LiveData<List<FeedArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg articles: FeedArticle) : List<Long>

    @Update
    suspend fun update(vararg articles: FeedArticle) : Int

    @Delete
    suspend fun delete(vararg articles: FeedArticle) : Int

    @Query("DELETE FROM ${Constants.TABLE_NAME_FEED_ARTICLES} WHERE ${Constants.COL_FEED_ID} = :feedId")
    suspend fun deleteArticlesByFeedId(feedId: Long): Int
}