package com.susheelkaram.myread.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import com.susheelkaram.myread.db.DB
import com.susheelkaram.myread.db.feeds_list.Feed
import com.susheelkaram.myread.db.feeds_list.FeedListRepo
import kotlinx.coroutines.async
import java.nio.charset.Charset

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class AddFeedViewModel(application: Application) : AndroidViewModel(application) {
    var feedUrl: String = ""
    var urlErrorText: String = ""
    private val db: DB = DB.getInstance(application)
    private var feedsRepo: FeedListRepo? = null
    private var rssParser: Parser? = null

    init {
        feedsRepo = FeedListRepo(db.feedListDao())
        rssParser =  Parser.Builder()
            .context(application)
            .charset(Charset.forName("ISO-8859-7"))
            .cacheExpirationMillis(24L * 60L * 60L * 100L) // one day
            .build()
    }

    fun onAddFeedClick() {
        viewModelScope.async {
            val feedUrlImmutable = feedUrl
            var channel : Channel? = rssParser?.getChannel(feedUrlImmutable)
            Log.d("FEED DETAILS", channel.toString())
            channel?.let {
                var feed = Feed(
                    feedUrl = feedUrlImmutable,
                    link = it.link ?: "",
                    description = it.description ?: "",
                    title = it.title ?: "",
                    imageUrl = it.image?.url ?: ""
                ).also {
                    feedsRepo?.addFeed(it)
                }
            }
        }
    }
}