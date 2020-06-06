package com.susheelkaram.myread.db.feeds_list

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class FeedListRepo(private val feedListDao: FeedListDao) {

    val feedList = feedListDao.getFeedList()

    suspend fun fetchFeedListNow(): List<Feed> {
        return feedListDao.getFeedListNow()
    }

    suspend fun addFeed(feed: Feed): List<Long> {
        return feedListDao.insertFeeds(feed)
    }

    suspend fun deleteFeed(feed: Feed): Boolean {
        return feedListDao.deleteFeeds(feed) > 0
    }
}