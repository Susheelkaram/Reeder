package com.susheelkaram.myread.di

import com.susheelkaram.myread.db.DB
import com.susheelkaram.myread.db.articles.ArticlesDao
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.feeds_list.FeedListDao
import com.susheelkaram.myread.db.feeds_list.FeedListRepo
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */


var dbModule = module {

    fun provideArticlesDoa(db: DB): ArticlesDao {
        return db.articlesListDao()
    }

    fun provideFeedsDao(db: DB): FeedListDao {
        return db.feedListDao()
    }

    single {
        DB.getInstance(androidApplication())
    }

    single {
        provideArticlesDoa(get())
    }
    single {
        provideFeedsDao(get())
    }

    single {
        ArticlesRepo(get())
    }

    single {
        FeedListRepo(get())
    }
}
