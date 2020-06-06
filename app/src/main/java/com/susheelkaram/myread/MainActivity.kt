package com.susheelkaram.myread

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.*
import com.susheelkaram.myread.adapter.HomePagerAdapter
import com.susheelkaram.myread.databinding.ActivityMainBinding
import com.susheelkaram.myread.syncing.FeedSyncingWorker
import com.susheelkaram.myread.utils.Constants
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var B: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupTabs()
        scheduleFeedSyncWorker()

//        FeedSyncingWorker.sendNewArticleNotification(this, listOf(
//            FeedArticle(title = "Slack’s new integration deal with AWS could also be about tweaking Microsoft"),
//            FeedArticle(title = "Stewart Butterfield says Microsoft sees Slack as existential threat")
//        ))
//
//        FeedSyncingWorker.sendNewArticleNotification(this, listOf(
//            FeedArticle(title = "Stewart Butterfield says Microsoft sees Slack as existential threat")
//        ))
    }

    private fun setupTabs() {
        var homePagerAdapter = HomePagerAdapter(this)
        B.pagerHome.adapter = homePagerAdapter

        B.pagerHome.isUserInputEnabled = false
        B.bottomNavigationView.selectedItemId = R.id.tab_Read

        B.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tab_Read -> B.pagerHome.setCurrentItem(0, false)
                R.id.tab_MyFeeds -> B.pagerHome.setCurrentItem(1, false)
                R.id.tab_Bookmarks -> B.pagerHome.setCurrentItem(2, false)
                else -> print("Screen not found")
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        if (B.pagerHome.currentItem == 0) {
            super.onBackPressed()
        } else {
            B.pagerHome.setCurrentItem(0, false)
        }
    }

    private fun scheduleFeedSyncWorker() {
        var constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        var feedSyncWorkRequest = PeriodicWorkRequest.Builder(
            FeedSyncingWorker::class.java,
            Constants.SYNC_INTERVAL,
            TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()


        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                Constants.FEED_SYNC_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                feedSyncWorkRequest
            )
    }
}

// TODO: Add isRead feature
// TODO: Improve Article item, Article Page & Add feed flow UI
// TODO: Add navigation Drawer for Feed selection
// TODO: Add in Share intent