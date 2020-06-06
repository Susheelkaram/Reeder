package com.susheelkaram.myread.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.prof.rssparser.Article
import com.susheelkaram.myread.db.DB
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.db.feeds_list.Feed
import com.susheelkaram.myread.db.feeds_list.FeedListRepo
import com.susheelkaram.myread.syncing.FeedHelper

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class AddFeedViewModel(application: Application) : AndroidViewModel(application) {
    var feedUrl: String = ""
    var urlErrorText: String = ""
    private val db: DB = DB.getInstance(application)
    private var feedsRepo: FeedListRepo? = null
    private var articlesRepo: ArticlesRepo? = null
    private var feedHelper = FeedHelper(application)

    init {
        feedsRepo = FeedListRepo(db.feedListDao())
        articlesRepo = ArticlesRepo(db.articlesListDao())
    }

    suspend fun addFeed(): Pair<Feed?, String> {
        return feedHelper.addFeed(feedUrl)
    }

    private suspend fun saveArticlesFromFeed(articles: List<Article>, feedId: Long) {
        var feedArticles: ArrayList<FeedArticle> = arrayListOf()
        articles.forEach {
            var feedArticle = FeedArticle.from(it)
            feedArticles.add(feedArticle.copy(feedId = feedId))
        }
        articlesRepo?.addArticles(feedArticles.toTypedArray())
    }
}