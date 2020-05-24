package com.susheelkaram.myread.db.feeds_list

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class FeedListRepo(private val feedListDao: FeedListDao) {

    val feedList = feedListDao.getFeedList()

    suspend fun addFeed(feed: Feed): Boolean {
        return feedListDao.insertFeeds(feed).isNotEmpty()
    }

    suspend fun deleteFeed(feed: Feed): Boolean {
        return feedListDao.deleteFeeds(feed) > 0
    }
}