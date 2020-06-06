package com.susheelkaram.myread.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.susheelkaram.myread.db.DB
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import kotlinx.coroutines.async

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val db: DB = DB.getInstance(application)
    private val articlesRepo: ArticlesRepo
    var articles: LiveData<List<FeedArticle>>
    var bookmarkedArticles: LiveData<List<FeedArticle>>

    init {
        articlesRepo = ArticlesRepo(db.articlesListDao())
        articles = articlesRepo.articles
        bookmarkedArticles = articlesRepo.bookmarkedArticles
    }

    fun onBookmark(isBookmarked: Boolean, article: FeedArticle) {
        viewModelScope.async {
            articlesRepo.updateArticle(
                article.copy(isBookmarked = isBookmarked)
            )
        }
    }

}