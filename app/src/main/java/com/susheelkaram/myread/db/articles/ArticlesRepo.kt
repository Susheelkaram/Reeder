package com.susheelkaram.myread.db.articles

import androidx.lifecycle.LiveData

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class ArticlesRepo(val articlesDao: ArticlesDao) {
    val articles: LiveData<List<FeedArticle>> = articlesDao.getAll()
    val bookmarkedArticles: LiveData<List<FeedArticle>> = articlesDao.getBookmarkedArticles()

    suspend fun fetchArticles() : List<FeedArticle> {
        return articlesDao.getAllNow()
    }
    suspend fun fetchArticlesByFeedId(feedId: Long) : List<FeedArticle> {
        return articlesDao.getArticlesByFeedIdSynchronously(feedId)
    }

    suspend fun addArticle(article: FeedArticle): Boolean {
        return articlesDao.insert(article).isNotEmpty()
    }

    suspend fun addArticles(articles: Array<FeedArticle>): Boolean {
        return articlesDao.insert(*articles).isNotEmpty()
    }

    suspend fun deleteArticle(article: FeedArticle): Boolean {
        return articlesDao.delete(article) > 0
    }

    suspend fun deleteArticles(articles: Array<FeedArticle>): Boolean {
        return articlesDao.delete(*articles) > 0
    }

    suspend fun deleteArticlesByFeedId(feedId: Long): Boolean {
        return articlesDao.deleteArticlesByFeedId(feedId) > 0
    }

    suspend fun updateArticle(article: FeedArticle): Boolean {
        return articlesDao.update(article) > 0
    }

    suspend fun updateArticles(articles: Array<FeedArticle>): Boolean {
        return articlesDao.update(*articles) > 0
    }


}