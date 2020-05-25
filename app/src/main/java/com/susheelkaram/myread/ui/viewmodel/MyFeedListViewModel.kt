package com.susheelkaram.myread.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.susheelkaram.myread.db.DB
import com.susheelkaram.myread.db.feeds_list.Feed
import com.susheelkaram.myread.db.feeds_list.FeedListRepo

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class MyFeedListViewModel(application: Application) : AndroidViewModel(application) {
    var feedList: LiveData<List<Feed>>
    private var feedListRepo : FeedListRepo? = null

    init {
        val db = DB.getInstance(application)
        feedListRepo = FeedListRepo(db.feedListDao())
        feedList = (feedListRepo as FeedListRepo).feedList
    }
}