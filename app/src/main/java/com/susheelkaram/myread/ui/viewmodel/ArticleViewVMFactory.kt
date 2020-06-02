package com.susheelkaram.myread.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.susheelkaram.myread.db.articles.FeedArticle

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class ArticleViewVMFactory(val application: Application, val article: FeedArticle) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleDetailViewModel(application, article) as T
    }
}