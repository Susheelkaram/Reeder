package com.susheelkaram.myread.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.susheelkaram.myread.adapter.ArticleListAdapter
import com.susheelkaram.myread.callbacks.ArticleItemAction
import com.susheelkaram.myread.databinding.FragmentBookmarksBinding
import com.susheelkaram.myread.db.DB
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.db.feeds_list.FeedListRepo
import com.susheelkaram.myread.ui.activities.ArticleDetailsActivity
import com.susheelkaram.myread.ui.viewmodel.HomeViewModel
import org.koin.android.ext.android.get

class BookmarksFragment : Fragment() {
    private lateinit var B: FragmentBookmarksBinding
    private lateinit var vm: HomeViewModel
    private lateinit var db: DB
    private var articlesRepo: ArticlesRepo = get<ArticlesRepo>()
    private var feedListRepo: FeedListRepo = get<FeedListRepo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        B = FragmentBookmarksBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArticleList()
    }

    private fun setupArticleList() {
        val adapter: ArticleListAdapter = ArticleListAdapter(
            requireContext(),
            object : ArticleItemAction<FeedArticle> {
                override fun onBookmarkClick(isBookmarked: Boolean, item: FeedArticle) {
                    vm.onBookmark(isBookmarked, item)
                }

                override fun onItemClick(type: String, item: FeedArticle) {
                    vm.markArticleAsRead(item)
                    var articleReadPageIntent = Intent(context, ArticleDetailsActivity::class.java)
                    articleReadPageIntent.putExtra("article", item)
                    requireContext().startActivity(articleReadPageIntent)
                }
            }
        )
        B.rvBookmarks.layoutManager = LinearLayoutManager(requireContext())
        B.rvBookmarks.adapter = adapter

        vm.feeds.observe(viewLifecycleOwner, Observer {
            adapter.setFeedlist(it)
        })

        vm.bookmarkedArticles.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

}
