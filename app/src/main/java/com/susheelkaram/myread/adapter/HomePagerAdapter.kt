package com.susheelkaram.myread.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.susheelkaram.myread.ui.BookmarksFragment
import com.susheelkaram.myread.ui.HomeFragment
import com.susheelkaram.myread.ui.MyFeedListFragment

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class HomePagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val allTabs = listOf<Fragment>(
        HomeFragment(),
        MyFeedListFragment(),
        BookmarksFragment()
    )

    override fun getItemCount(): Int {
        return allTabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return allTabs[position]
    }
}