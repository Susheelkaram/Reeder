package com.susheelkaram.myread.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.susheelkaram.myread.R
import com.susheelkaram.myread.adapter.ArticleListAdapter
import com.susheelkaram.myread.callbacks.ArticleItemAction
import com.susheelkaram.myread.databinding.FragmentHomeBinding
import com.susheelkaram.myread.db.DB
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.ui.viewmodel.HomeViewModel
import com.susheelkaram.myread.utils.FragmentToolbar
import com.susheelkaram.myread.utils.MenuClick
import com.susheelkaram.myread.utils.Utils

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {

    private lateinit var B: FragmentHomeBinding
    private lateinit var vm: HomeViewModel
    private lateinit var db: DB
    private var articlesRepo: ArticlesRepo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        B = FragmentHomeBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        db = DB.getInstance(requireContext())
        articlesRepo = ArticlesRepo(db.articlesListDao())
        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArticleList()
    }

    override fun onBuildToolbar(): FragmentToolbar {
        var fragmentToolbarBuilder = FragmentToolbar.Builder()
            .setResId(R.id.toolbar_Home)
            .setTitle(R.string.fragment_read)
            .setMenu(R.menu.menu_home)
            .setMenuItemClickListener(MenuClick { item ->
                when (item.itemId) {
                    R.id.menu_switch_dark_mode -> Utils.switchDarkLightTheme()
                }
            })
        return fragmentToolbarBuilder.build()
    }

    private fun setupArticleList() {
        val adapter: ArticleListAdapter = ArticleListAdapter(
            requireContext(),
            object : ArticleItemAction {
                override fun onBookmarkClick(isBookmarked: Boolean, item: FeedArticle) {
                    vm.onBookmark(isBookmarked, item)
                }

                override fun onDelete(item: FeedArticle) {
                    TODO("Not yet implemented")
                }
            }
        )
        B.rvArticles.layoutManager = LinearLayoutManager(requireContext())
        B.rvArticles.adapter = adapter
        vm.articles.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }


}
