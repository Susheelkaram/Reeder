package com.susheelkaram.myread.ui.activities

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.susheelkaram.myread.R
import com.susheelkaram.myread.databinding.ActivityAddFeedBinding
import com.susheelkaram.myread.ui.viewmodel.AddFeedViewModel
import com.susheelkaram.trackky.utils.hide
import com.susheelkaram.trackky.utils.show
import com.susheelkaram.trackky.utils.showToast
import kotlinx.coroutines.*

class AddFeedActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var B: ActivityAddFeedBinding
    lateinit var vm: AddFeedViewModel

    private var job = Job()
    var coroutinScope = CoroutineScope(job + Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = DataBindingUtil.setContentView(this, R.layout.activity_add_feed)
        vm = ViewModelProvider(this).get(AddFeedViewModel::class.java)
        B.vm = vm

        B.btnSubmitAddFeed.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_SubmitAddFeed -> addFeed()
        }
    }

    private fun addFeed() {
        B.progressAddFeed.show()
        coroutinScope.launch {
            var newFeedResult = vm.addFeed()
            withContext(Dispatchers.Main) {
                B.progressAddFeed.hide()
                applicationContext.showToast(newFeedResult.second)
                if(newFeedResult.first != null) finish()
            }
        }
    }

    fun isFormValid() : Boolean {
        if(!Patterns.WEB_URL.matcher(B.inputFeedUrl.text).matches()) {
            B.inputFeedUrl.error = ""
        }
        return true
    }

}
