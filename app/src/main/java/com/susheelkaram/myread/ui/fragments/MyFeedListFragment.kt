package com.susheelkaram.myread.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.susheelkaram.myread.R
import com.susheelkaram.myread.adapter.FeedListAdapter
import com.susheelkaram.myread.callbacks.RecyclerViewCallBack

import com.susheelkaram.myread.databinding.FragmentMyFeedListBinding
import com.susheelkaram.myread.db.feeds_list.Feed
import com.susheelkaram.myread.ui.activities.AddFeedActivity
import com.susheelkaram.myread.ui.viewmodel.MyFeedListViewModel
import com.susheelkaram.myread.utils.FragmentToolbar
import com.susheelkaram.myread.utils.MenuClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class MyFeedListFragment : BaseFragment(), View.OnClickListener {

    lateinit var B: FragmentMyFeedListBinding
    lateinit var vm: MyFeedListViewModel
    private var job = Job()
    private var coroutineScope = CoroutineScope(job + Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        B = FragmentMyFeedListBinding.inflate(inflater, container, false)
        return B.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this).get(MyFeedListViewModel::class.java)

        B.btnAddFeed.setOnClickListener(this)
        setupFeedList()
    }

    override fun onBuildToolbar(): FragmentToolbar {
        var onItemClick = MenuClick { item ->
            when (item.itemId) {
                R.id.menu_add_feed -> openFeedEditor()
            }
        }
        return FragmentToolbar.Builder()
            .setResId(R.id.toolbar_MyFeeds)
            .setTitle(R.string.fragment_my_feeds)
            .setMenu(R.menu.menu_toolbar_my_feeds)
            .setMenuItemClickListener(onItemClick)
            .build()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_AddFeed -> openFeedEditor()
        }
    }

    private fun openFeedEditor() {
        var intent = Intent(requireContext(), AddFeedActivity::class.java)
        startActivity(intent)
    }

    private fun setupFeedList() {
        var feedListAdapter = FeedListAdapter(requireContext(), RecyclerViewCallBack {
                type, item ->  showFeedOptions(item)

        })
        B.rvFeedList.adapter = feedListAdapter
        B.rvFeedList.layoutManager = LinearLayoutManager(requireContext())
        vm.feedList.observe(viewLifecycleOwner, Observer {
            feedListAdapter.setData(it)
        })
    }

    private fun showFeedOptions(feed: Feed) {
        var options = arrayOf("Delete feed")

        AlertDialog.Builder(requireContext())
            .setTitle("Options")
            .setItems(options, DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> coroutineScope.async {
                        vm.deleteFeed(feed)
                    }
                }

            }).create().show()
    }
}
