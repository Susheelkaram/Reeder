package com.susheelkaram.myread.syncing

import android.content.Context
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.db.feeds_list.Feed
import com.susheelkaram.myread.db.feeds_list.FeedListRepo
import org.koin.core.KoinComponent
import org.koin.core.get

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class FeedSync(context: Context) : KoinComponent {
    private var feedList: List<Feed> = listOf()
    private var articlesRepo: ArticlesRepo = get<ArticlesRepo>()
    private var feedRepo: FeedListRepo = get<FeedListRepo>()
    private var feedHelper = FeedHelper(get())


    suspend fun sync(): List<FeedArticle> {
        feedList = feedRepo.fetchFeedListNow()

        var newArticles = mutableListOf<FeedArticle>()
        if (feedList.isNotEmpty()) {
            var allArticles = articlesRepo.fetchArticles()

            for (feed in feedList) {
                var newArticlesInThisFeed = syncFeed(feed, allArticles)
                newArticles.addAll(newArticlesInThisFeed)
            }
        }

        return newArticles
    }

    suspend fun syncFeed(feed: Feed, allArticles: List<FeedArticle>): List<FeedArticle> {
        var channel = feedHelper.fetchFeed(feed.feedUrl)

        channel?.let {
            var fetchedArticles = FeedHelper.getFeedArticleListFromArticles(feed, channel.articles)
            var oldArticlesFromSameFeed = allArticles.filter { feed.id == it.feedId }

            var articlesToSave = getUniqueArticles(fetchedArticles, oldArticlesFromSameFeed)
            articlesRepo.addArticles(articlesToSave.toTypedArray())
            return articlesToSave
        }
        return listOf()
    }

    private fun getUniqueArticles(
        newArticles: List<FeedArticle>,
        oldArticles: List<FeedArticle>
    ): List<FeedArticle> {
        return newArticles.filterNot { a ->
            oldArticles.any { it ->
                it.guid.equals(a.guid, ignoreCase = true)
            }
        }
    }
}