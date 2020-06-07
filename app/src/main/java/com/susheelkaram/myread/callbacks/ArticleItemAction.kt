package com.susheelkaram.myread.callbacks

import com.susheelkaram.myread.db.articles.FeedArticle

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
interface ArticleItemAction<T>: RecyclerViewCallback<T> {
    fun onBookmarkClick(isBookmarked: Boolean, item: FeedArticle)
}