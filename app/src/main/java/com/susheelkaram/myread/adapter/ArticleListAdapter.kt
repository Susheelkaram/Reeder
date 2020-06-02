package com.susheelkaram.myread.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.susheelkaram.myread.R
import com.susheelkaram.myread.callbacks.ArticleItemAction
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.ui.activities.ArticleDetailsActivity

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
        holder.txtArticleDescription.text = HtmlCompat.fromHtml(article.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        holder.txtArticleTimeStamp.text = article.pubDate


        // To avoid unnecessary checking when Views are Recycled
        holder.checkBoxBookmark.setOnCheckedChangeListener(null)

        holder.itemView.setOnClickListener {
            var articleReadPageIntent = Intent(context, ArticleDetailsActivity::class.java)
            articleReadPageIntent.putExtra("article", article)
            context.startActivity(articleReadPageIntent)
        }

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