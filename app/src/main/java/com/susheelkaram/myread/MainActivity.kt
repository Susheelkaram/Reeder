package com.susheelkaram.myread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.susheelkaram.myread.adapter.HomePagerAdapter
import com.susheelkaram.myread.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var B: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupTabs()
    }

    fun setupTabs() {
        var homePagerAdapter = HomePagerAdapter(this)
        B.pagerHome.adapter = homePagerAdapter

        B.pagerHome.isUserInputEnabled = false
        B.bottomNavigationView.selectedItemId = R.id.tab_Read

        B.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.tab_Read -> B.pagerHome.setCurrentItem(0, false)
                R.id.tab_MyFeeds -> B.pagerHome.setCurrentItem(1, false)
                R.id.tab_Bookmarks -> B.pagerHome.setCurrentItem(2, false)
                else -> print("Screen not found")
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        if(B.pagerHome.currentItem == 0) {
            super.onBackPressed()
        }
        else {
            B.pagerHome.setCurrentItem(0, false)
        }
    }
}
