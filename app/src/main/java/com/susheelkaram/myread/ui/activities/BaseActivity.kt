package com.susheelkaram.myread.ui.activities

import androidx.appcompat.app.AppCompatActivity
import com.susheelkaram.myread.utils.FragmentToolbar
import com.susheelkaram.myread.utils.ToolbarManager

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        ToolbarManager(onBuildToolbar()).setupToolbar()
    }


    abstract fun onBuildToolbar(): FragmentToolbar
}