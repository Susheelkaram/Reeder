package com.susheelkaram.myread.syncing

import android.app.Application
import android.util.Log
import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.db.feeds_list.Feed
import com.susheelkaram.myread.db.feeds_list.FeedListRepo
import org.koin.core.KoinComponent
import org.koin.core.get
import java.nio.charset.Charset

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
enum class Result {
    SUCCESS, FAILED
}

class FeedHelper(
    private val application: Application,
    private val onFetchResult: FetchFeedCallback? = null
) :
    KoinComponent {
    private val LOG_TAG = "FeedHelper.class"
    var feedsRepo = get<FeedListRepo>()
    var articlesRepo = get<ArticlesRepo>()

    companion object {
        fun getFeedArticleFromArticle(feed: Feed, article: Article): FeedArticle {
            return FeedArticle.from(article).copy(feedId = feed.id)
        }

        fun getFeedArticleListFromArticles(feed: Feed, articles: List<Article>): List<FeedArticle> {
            var feedArticles = mutableListOf<FeedArticle>()
            for(article in articles) {
                feedArticles.add(getFeedArticleFromArticle(feed, article))
            }
            return feedArticles
        }
    }

    var rssParser = Parser.Builder()
        .context(application)
        .charset(Charset.forName("ISO-8859-7"))
        .cacheExpirationMillis(24L * 60L * 60L * 100L) // one day
        .build()

    suspend fun fetchFeed(feedUrl: String): Channel? {
        var channel: Channel? = null
        try {
            channel = rssParser?.getChannel(feedUrl)
        } catch (e: Exception) {
            Log.e(LOG_TAG, e.toString())
            onFetchResult?.onFail("Unable to fetch feed details. Check feed Url", feedUrl)
        }
        return channel
    }

    fun doesFeedExist(feedUrl: String, feedList: List<Feed>): Boolean {
        var currentUrl = convertUrlToFormat(feedUrl)
        for (feed in feedList) {
            if (convertUrlToFormat(feed.feedUrl) == currentUrl) {
                return true
            }
        }
        return false
    }

    private fun convertUrlToFormat(url: String): String {
        var newUrl = url.toLowerCase()
        newUrl = newUrl.replace("https", "http")
        newUrl = newUrl.replace("www.", "")
        if (newUrl.endsWith('/') || newUrl.endsWith('\\')) {
            newUrl = newUrl.substring(0, newUrl.length - 1)
        }
        return newUrl
    }

    suspend fun addFeed(feedUrl: String): Pair<Feed?, String> {
        var feeds = feedsRepo.fetchFeedListNow()

        var addedFeed: Feed? = null

        if (doesFeedExist(feedUrl, feeds)) {
            onFetchResult?.onFail("Feed already exists", feedUrl)
            return Pair(null, "Feed already exists")
        }

        var channel = fetchFeed(feedUrl)
        channel?.let {
            addedFeed = Feed(
                feedUrl = feedUrl,
                link = it.link ?: "",
                description = it.description ?: "",
                title = it.title ?: "",
                imageUrl = it.image?.url ?: ""
            ).also {
                val ids = feedsRepo?.addFeed(it) ?: listOf()
                if (ids.isNotEmpty()) saveArticlesFromFeed(channel.articles, it.copy(id = ids[0]))
            }
            onFetchResult?.onSuccess(feedUrl, addedFeed!!)

            return Pair(addedFeed,"Feed Added successfully")
        }

        return Pair(addedFeed,"Unable to fetch feed details. Check feed Url")
    }

    private suspend fun saveArticlesFromFeed(articles: List<Article>, feed: Feed) {
        var feedArticles = getFeedArticleListFromArticles(feed, articles)
        articlesRepo?.addArticles(feedArticles.toTypedArray())
    }
}


interface FetchFeedCallback {
    fun onSuccess(url: String, feed: Feed)
    fun onFail(errorMessage: String, url: String)
}