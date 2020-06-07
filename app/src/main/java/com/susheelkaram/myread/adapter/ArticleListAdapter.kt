package com.susheelkaram.myread.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.susheelkaram.myread.R
import com.susheelkaram.myread.callbacks.ArticleItemAction
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.db.feeds_list.Feed
import com.susheelkaram.myread.utils.Utils

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class ArticleListAdapter(
    val context: Context,
    private val onItemAction: ArticleItemAction<FeedArticle>
) : RecyclerView.Adapter<ArticleListAdapter.ArticleVH>() {

    private var articlesList = listOf<FeedArticle>()
    private var feedList = listOf<Feed>()

    companion object {
        const val BTN_DELETE = "btnDelete"
        const val BTN_BOOKMARK = "btnBookmark"
    }

    class ArticleVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtArticleTitle = itemView.findViewById<TextView>(R.id.txt_ArticleTitle)
        val txtArticleTimeStamp = itemView.findViewById<TextView>(R.id.txt_ArticleTimestamp)
        val txtFeedName = itemView.findViewById<TextView>(R.id.txt_FeedName)
        val imgFeedLogo = itemView.findViewById<ImageView>(R.id.img_FeedLogo)
        val checkBoxBookmark = itemView.findViewById<CheckBox>(R.id.checkbox_Bookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        val v = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return ArticleVH(v)
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

    private fun setReadStatus(titleView: TextView, isRead: Boolean) {
        TextViewCompat.setTextAppearance(titleView, R.style.articleTitleStyle)
        if (isRead) TextViewCompat.setTextAppearance(titleView, R.style.articleReadTitleStyle)
    }

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        var article = articlesList[position]
        var feed = feedList.find { feed -> article.feedId == feed.id }

        feed?.let {
            val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
            Glide.with(context)
                .load(if(!feed.imageUrl.isNullOrEmpty()) feed.imageUrl else R.drawable.rss_feed_default_logo)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .placeholder(R.drawable.rss_feed_default_logo)
                .into(holder.imgFeedLogo)
            holder.txtFeedName.text = feed.title
        }

        // To avoid unnecessary checking when Views are Recycled
        holder.checkBoxBookmark.setOnCheckedChangeListener(null)

        setReadStatus(holder.txtArticleTitle, article.isRead)

        holder.txtArticleTitle.text = article.title
        holder.txtArticleTimeStamp.text =
            Utils.getFormattedTimeStamp(article.pubDate, isMillis = true)

        holder.itemView.setOnClickListener {
            onItemAction.onItemClick("item", article)
        }

        holder.checkBoxBookmark.isChecked = article.isBookmarked
        holder.checkBoxBookmark.setOnCheckedChangeListener { buttonView, isChecked ->
            articlesList[position].isBookmarked = isChecked
            onItemAction.onBookmarkClick(isChecked, article)
        }
    }

    fun setData(articles: List<FeedArticle>) {
        articlesList = articles
        notifyDataSetChanged()
    }

    fun setFeedlist(feeds: List<Feed>) {
        feedList = feeds
        notifyDataSetChanged()
    }
}