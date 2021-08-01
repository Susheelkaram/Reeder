package com.susheelkaram.myread.ui.activities

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.susheelkaram.myread.utils.FragmentToolbar
import com.susheelkaram.myread.utils.ToolbarManager

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
abstract class BaseActivity : AppCompatActivity() {
    var toolbar : FragmentToolbar? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        toolbar = onBuildToolbar()
        toolbar?.let{
            setSupportActionBar(it.toolbar)
            supportActionBar?.title =
                if (it.title != FragmentToolbar.NO_TOOLBAR) getString(it.title) else "" +
                        ""
        }
    }


    abstract fun onBuildToolbar(): FragmentToolbar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(toolbar!!.menuId, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
            else -> toolbar?.onMenuItemClick?.onMenuItemClick(item)
        }
        return super.onOptionsItemSelected(item)
    }


}