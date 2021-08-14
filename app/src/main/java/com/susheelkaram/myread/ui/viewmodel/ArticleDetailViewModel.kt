package com.susheelkaram.myread.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.susheelkaram.myread.db.DB
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.ui.model.UiEvent
import com.susheelkaram.myread.utils.SingleLiveEvent
import kotlinx.coroutines.async

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class ArticleDetailViewModel(application: Application, val article: FeedArticle) : AndroidViewModel(application) {
    private var articleRepo : ArticlesRepo
    val uiEvent =  SingleLiveEvent<UiEvent>()

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

    fun shareArticle() {
        val shareText = "${article.title} - ${article.link} (sent via Reeder app)"
        uiEvent.setValue(UiEvent.Navigate.Implicit("share", shareText))
    }

    fun openArticleInBrowser() {
        uiEvent.setValue(UiEvent.Navigate.Implicit("view", article.link))
    }
}