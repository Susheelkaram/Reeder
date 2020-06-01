package com.susheelkaram.myread.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.susheelkaram.myread.R
import com.susheelkaram.myread.callbacks.ArticleItemAction
import com.susheelkaram.myread.callbacks.RecyclerViewCallback
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class ArticleListAdapter(
    val context: Context,
    val onItemAction: ArticleItemAction
) : RecyclerView.Adapter<ArticleListAdapter.ArticleVH>() {

    private var articlesList = listOf<FeedArticle>()

    companion object {
        const val BTN_DELETE = "btnDelete"
        const val BTN_BOOKMARK = "btnBookmark"
    }

    class ArticleVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtArticleTitle = itemView.findViewById<TextView>(R.id.txt_ArticleTitle)
        val txtArticleDescription = itemView.findViewById<TextView>(R.id.txt_ArticleDescription)
        val txtArticleTimeStamp = itemView.findViewById<TextView>(R.id.txt_ArticleTimestamp)
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

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        var article = articlesList[position]
        holder.txtArticleTitle.text = article.title
        holder.txtArticleDescription.text = article.description
        holder.txtArticleTimeStamp.text = article.pubDate

        // To avoid unnecessary checking when Views are Recycled
        holder.checkBoxBookmark.setOnCheckedChangeListener(null)

        holder.checkBoxBookmark.isChecked = article.isBookMarked
        holder.checkBoxBookmark.setOnCheckedChangeListener { buttonView, isChecked ->
            articlesList[position].isBookMarked = isChecked
            onItemAction.onBookmarkClick(isChecked, article)
        }
    }

    fun setData(articles: List<FeedArticle>) {
        articlesList = articles
        notifyDataSetChanged()
    }
}