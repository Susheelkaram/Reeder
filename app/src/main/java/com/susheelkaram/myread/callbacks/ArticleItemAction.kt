package com.susheelkaram.myread.callbacks

import com.susheelkaram.myread.db.articles.FeedArticle

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
interface ArticleItemAction {
    fun onBookmarkClick(isBookmarked: Boolean, item: FeedArticle)
    fun onDelete(item: FeedArticle)
}