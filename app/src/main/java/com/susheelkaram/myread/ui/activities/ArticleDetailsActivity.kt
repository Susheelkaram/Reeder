package com.susheelkaram.myread.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.susheelkaram.myread.R
import com.susheelkaram.myread.databinding.ActivityArticleDetailsBinding
import com.susheelkaram.myread.db.articles.FeedArticle
import com.susheelkaram.myread.ui.viewmodel.ArticleDetailViewModel
import com.susheelkaram.myread.ui.viewmodel.ArticleViewVMFactory
import com.susheelkaram.myread.utils.FragmentToolbar
import com.susheelkaram.myread.utils.MenuClick
import com.susheelkaram.myread.utils.Utils
import com.susheelkaram.myread.utils.ThemeUtil

class ArticleDetailsActivity : BaseActivity() {
    lateinit var B: ActivityArticleDetailsBinding
    lateinit var vm: ArticleDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = DataBindingUtil.setContentView(this, R.layout.activity_article_details)

        var article = intent.getParcelableExtra<FeedArticle>("article")
        vm = ViewModelProvider(this, ArticleViewVMFactory(application, article)).get(
            ArticleDetailViewModel::class.java
        )
        setData()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setData() {
        vm.article?.let {
            B.txtReadTitle.text = vm.article.title
            B.txtReadBody.text =
                HtmlCompat.fromHtml(vm.article.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            if (vm.article.image.isNotEmpty()) {
                Glide.with(this)
                    .load(vm.article.image)
                    .placeholder(R.drawable.rectangle_block)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(B.imgFeatureImage)
            }
            B.btnOpenArticleLink.setOnClickListener {
                if (vm.article.link.isNotEmpty()) {
                    var openOriginalLinkIntent = Intent(Intent.ACTION_VIEW)
                    openOriginalLinkIntent.data = Uri.parse(vm.article.link)
                    startActivity(openOriginalLinkIntent)
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val superVal = super.onCreateOptionsMenu(menu)
        val bookmarkMenuItem = B.toolbarReadArticle.menu.findItem(R.id.menu_bookmark)
        val themeModeMenuItem = B.toolbarReadArticle.menu.findItem(R.id.menu_switch_dark_mode_read)
        val themeMode = AppCompatDelegate.getDefaultNightMode()
        vm.article?.let {
            setBookmarkIconState(bookmarkMenuItem, it.isBookmarked)
        }
        themeModeMenuItem.setIcon(ThemeUtil.getThemeIcon(themeMode))
        return superVal
    }
    override fun onBuildToolbar(): FragmentToolbar {
        var toolbar = findViewById<Toolbar>(R.id.toolbar_ReadArticle)
        var fragmentToolbarBuilder = FragmentToolbar.Builder()
            .setToolbar(toolbar)
            .setMenu(R.menu.menu_article_read_page)
            .setMenuItemClickListener(MenuClick { item ->
                when (item.itemId) {
                    R.id.menu_bookmark -> onBookmarkOptionClicked(item)
                    R.id.menu_switch_dark_mode_read -> ThemeUtil.switchThemeAndSetMenuIcon(application, item)
                }
            })
        return fragmentToolbarBuilder.build()
    }

    private fun onBookmarkOptionClicked(item: MenuItem) {
        val isChecked = !item.isChecked
        vm.setBookmark(isChecked)
        setBookmarkIconState(item, isChecked)
    }

    private fun setBookmarkIconState(menuItem: MenuItem, isBookmarked: Boolean) {
        menuItem.setChecked(isBookmarked)
        menuItem.setIcon(if (isBookmarked) R.drawable.ic_bookmark_black_24dp else R.drawable.ic_bookmark_border_black_24dp)
    }
}
