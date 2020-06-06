package com.susheelkaram.myread.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.feeds_list.Feed
import com.susheelkaram.myread.db.feeds_list.FeedListRepo
import org.koin.core.KoinComponent
import org.koin.core.get

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class MyFeedListViewModel(application: Application) : AndroidViewModel(application), KoinComponent {
    var feedList: LiveData<List<Feed>>
    private var feedListRepo : FeedListRepo = get<FeedListRepo>()
    private var articleListRepo : ArticlesRepo = get<ArticlesRepo>()

    init {
        feedList = (feedListRepo as FeedListRepo).feedList
    }

    suspend fun deleteFeed(feed: Feed) {
        var isFeedDeleted = feedListRepo.deleteFeed(feed)
        if(isFeedDeleted) {
            articleListRepo.deleteArticlesByFeedId(feed.id)
        }
    }
}