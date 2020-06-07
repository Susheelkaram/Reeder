package com.susheelkaram.myread.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.db.feeds_list.Feed
import com.susheelkaram.myread.db.feeds_list.FeedListRepo
import kotlinx.coroutines.async
import org.koin.core.KoinComponent
import org.koin.core.get

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class HomeViewModel(application: Application) : AndroidViewModel(application), KoinComponent {
    private var articlesRepo: ArticlesRepo = get<ArticlesRepo>()
    private var feedsRepo: FeedListRepo = get<FeedListRepo>()
    var articles: LiveData<List<FeedArticle>>
    var feeds: LiveData<List<Feed>>
    var bookmarkedArticles: LiveData<List<FeedArticle>>

    init {
        articles = articlesRepo.articles
        feeds = feedsRepo.feedList
        bookmarkedArticles = articlesRepo.bookmarkedArticles
    }

    fun onBookmark(isBookmarked: Boolean, article: FeedArticle) {
        viewModelScope.async {
            articlesRepo.updateArticle(
                article.copy(isBookmarked = isBookmarked)
            )
        }
    }

    fun markArticleAsRead(article: FeedArticle) {
        viewModelScope.async {
            article.isRead = true
            articlesRepo.updateArticle(article)
        }
    }

}