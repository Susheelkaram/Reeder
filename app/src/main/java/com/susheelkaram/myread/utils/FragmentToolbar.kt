package com.susheelkaram.myread.utils

import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class FragmentToolbar(
    @StringRes val title: Int,
    @IdRes val resId: Int,
    @MenuRes val menuId: Int,
    val toolbar: Toolbar?,
    val onMenuItemClick: MenuClick?
) {

    companion object {
        @JvmField
        val NO_TOOLBAR = -1
    }

    class Builder {
        private var title: Int = -1
        private var resId: Int = -1
        private var menu: Int = -1
        private var onMenuItemClick: MenuClick? = null
        private var toolbar: Toolbar? = null

        fun setTitle(title: Int) = apply { this.title = title }
        fun setResId(@IdRes resId: Int) = apply { this.resId = resId }
        fun setMenu(@MenuRes menuId: Int) = apply { this.menu = menuId }
        fun setMenuItemClickListener(onMenuItemClick: MenuClick) =
            apply { this.onMenuItemClick = onMenuItemClick }

        fun setToolbar(toolbar: Toolbar) = apply { this.toolbar = toolbar }
        fun build(): FragmentToolbar {
            return FragmentToolbar(title, resId, menu, toolbar, onMenuItemClick)
        }
    }
}

interface MenuClick {
    fun onMenuItemClick(item: MenuItem)
}

fun MenuClick(lamda: (item: MenuItem) -> Unit) : MenuClick {
    return object : MenuClick {
        override fun onMenuItemClick(item: MenuItem) {
            lamda(item)
        }
    }
}
