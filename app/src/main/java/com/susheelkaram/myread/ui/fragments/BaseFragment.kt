package com.susheelkaram.myread.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.susheelkaram.myread.R
import com.susheelkaram.myread.utils.FragmentToolbar
import com.susheelkaram.myread.utils.ToolbarManager

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ToolbarManager(onBuildToolbar(), view).setupToolbar()
    }

    abstract fun onBuildToolbar() : FragmentToolbar
}
