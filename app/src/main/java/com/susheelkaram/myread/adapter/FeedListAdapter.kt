package com.susheelkaram.myread.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.susheelkaram.myread.R
import com.susheelkaram.myread.db.feeds_list.Feed

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class FeedListAdapter(private val context: Context) :
    RecyclerView.Adapter<FeedListAdapter.FeedListVH>() {

    private var feedList = listOf<Feed>()

    class FeedListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var feedImage = itemView.findViewById<ImageView>(R.id.img_FeedImg)
        var feedTitle = itemView.findViewById<TextView>(R.id.txt_FeedTitle)
        var feedUrl = itemView.findViewById<TextView>(R.id.txt_FeedUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedListVH {
        var v = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false)
        return FeedListVH(v)
    }

    override fun getItemCount(): Int {
        return feedList.size
    }

    override fun onBindViewHolder(holder: FeedListVH, position: Int) {
        var feed = feedList[position]

        holder.feedTitle.text = feed.title
        holder.feedUrl.text = feed.feedUrl
        if(!feed.imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(feed.imageUrl)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.feedImage)
        }
    }

    fun setData(feeds: List<Feed>) {
        feedList = feeds
        notifyDataSetChanged()
    }
}