package com.susheelkaram.myread.utils

import android.content.Context
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.iterator

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class ToolbarManager(private val toolbarData: FragmentToolbar, private val container: View) {


    fun setupToolbar() {
        if(toolbarData.resId != FragmentToolbar.NO_TOOLBAR) {
            var toolbar = container.findViewById(toolbarData.resId) as Toolbar

            if (toolbarData.title != -1) {
                toolbar.setTitle(toolbarData.title)
            }

            var context: Context? = null

            if (toolbarData.menuId != -1) {
                toolbar.inflateMenu(toolbarData.menuId)
                var menu = toolbar.menu
                for(item in menu.iterator()) {
                    item.setOnMenuItemClickListener {
                        toolbarData.onMenuItemClick?.onMenuItemClick(it)
                        true
                    }
                }
            }

        }
    }
}