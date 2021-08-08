package com.susheelkaram.myread.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.susheelkaram.myread.R
import com.susheelkaram.myread.adapter.ArticleListAdapter
import com.susheelkaram.myread.callbacks.ArticleItemAction
import com.susheelkaram.myread.databinding.FragmentHomeBinding
import com.susheelkaram.myread.db.articles.ArticlesRepo
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.db.feeds_list.FeedListRepo
import com.susheelkaram.myread.syncing.FeedSync
import com.susheelkaram.myread.ui.activities.AddFeedActivity
import com.susheelkaram.myread.ui.activities.ArticleDetailsActivity
import com.susheelkaram.myread.ui.viewmodel.HomeViewModel
import com.susheelkaram.myread.utils.FragmentToolbar
import com.susheelkaram.myread.utils.MenuClick
import com.susheelkaram.myread.utils.ThemeUtil
import com.susheelkaram.myread.utils.Utils
import com.susheelkaram.trackky.utils.hide
import com.susheelkaram.trackky.utils.show
import com.susheelkaram.trackky.utils.showToast
import kotlinx.coroutines.*
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment(), View.OnClickListener {

    private lateinit var B: FragmentHomeBinding
    private lateinit var vm: HomeViewModel
    private var articlesRepo: ArticlesRepo = get<ArticlesRepo>()
    private var feedsRepo: FeedListRepo = get<FeedListRepo>()
    private var feedSync : FeedSync? = null

    private var job = Job()
    private var coroutineScope = CoroutineScope(job + Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        B = FragmentHomeBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this).get(HomeViewModel::class.java)
        feedSync = FeedSync(requireContext())
        return B.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setThemeModeIcon()
        B.btnAddFeedHome.setOnClickListener(this)
        setupArticleList()
    }

    override fun onBuildToolbar(): FragmentToolbar {
        var fragmentToolbarBuilder = FragmentToolbar.Builder()
            .setResId(R.id.toolbar_Home)
            .setTitle(R.string.fragment_read)
            .setMenu(R.menu.menu_home)
            .setMenuItemClickListener(MenuClick { item ->
                when (item.itemId) {
                    R.id.menu_switch_dark_mode -> activity?.application?.let {
                        ThemeUtil.switchThemeAndSetMenuIcon(it, item)
                    }
                    R.id.menu_refresh_feeds -> {
                        B.refreshLayoutFeeds.isRefreshing = true
                        refreshFeeds()
                    }
                }
            })
        return fragmentToolbarBuilder.build()
    }

    fun setThemeModeIcon() {
        val themeModeMenuItem = B.toolbarHome.menu.findItem(R.id.menu_switch_dark_mode)
        val themeMode = AppCompatDelegate.getDefaultNightMode()
        themeModeMenuItem.setIcon(ThemeUtil.getThemeIcon(themeMode))
    }

    private fun refreshFeeds() {
        coroutineScope.async {
            feedSync?.sync()
            withContext(Dispatchers.Main) {
                requireContext().showToast("Feeds refreshed")
                B.refreshLayoutFeeds.isRefreshing = false
            }
        }
    }

    private fun setupArticleList() {
        val adapter: ArticleListAdapter = ArticleListAdapter(
            requireContext(),
            object : ArticleItemAction<FeedArticle> {
                override fun onBookmarkClick(isBookmarked: Boolean, item: FeedArticle) {
                    vm.onBookmark(isBookmarked, item)
                }

                override fun onItemClick(type: String, article: FeedArticle) {
                    vm.markArticleAsRead(article)
                    var articleReadPageIntent = Intent(context, ArticleDetailsActivity::class.java)
                    articleReadPageIntent.putExtra("article", article)
                    requireContext().startActivity(articleReadPageIntent)
                }
            }
        )
        B.rvArticles.layoutManager = LinearLayoutManager(requireContext())
        B.rvArticles.adapter = adapter
        B.rvArticles.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        B.refreshLayoutFeeds.setOnRefreshListener {
            refreshFeeds()
        }

        vm.feeds.observe(viewLifecycleOwner, Observer {
            adapter.setFeedlist(it)
        })
        vm.articles.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()) {
                B.rvArticles.hide()
                B.llEmptyView.show()
            }
            else {
                B.rvArticles.show()
                B.llEmptyView.hide()
            }
            adapter.setData(it)
        })
    }

    private fun openFeedEditor() {
        var intent = Intent(requireContext(), AddFeedActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_AddFeedHome -> openFeedEditor()
        }
    }

}
