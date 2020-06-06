package com.susheelkaram.myread.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.susheelkaram.myread.db.DB
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import kotlinx.coroutines.async

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class ArticleDetailViewModel(application: Application, val article: FeedArticle) : AndroidViewModel(application) {
    private var articleRepo : ArticlesRepo

    init {
        var db = DB.getInstance(getApplication())
        articleRepo = ArticlesRepo(db.articlesListDao())
    }

    fun setBookmark(isBookMarked: Boolean) {
        article?.let {
           viewModelScope.async {
               articleRepo.updateArticle(it.copy(isBookmarked = isBookMarked))
           }
        }
    }
}