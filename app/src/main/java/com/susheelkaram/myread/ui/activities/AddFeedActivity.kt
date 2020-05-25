package com.susheelkaram.myread.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.susheelkaram.myread.R
import com.susheelkaram.myread.databinding.ActivityAddFeedBinding
import com.susheelkaram.myread.ui.viewmodel.AddFeedViewModel

class AddFeedActivity : AppCompatActivity() {
    lateinit var B: ActivityAddFeedBinding
    lateinit var vm: AddFeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = DataBindingUtil.setContentView(this, R.layout.activity_add_feed)
        vm = ViewModelProvider(this).get(AddFeedViewModel::class.java)
        B.vm = vm
    }
}
