package com.example.customnews.ui.main

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.customnews.FragOne
import com.example.customnews.FragThree
import com.example.customnews.FragTwo
import com.example.customnews.R

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 ->FragOne()
            1 ->FragTwo()
            else -> return FragThree()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Top Stories"
            1 -> "Most popular"
            else -> return "Business"
        }
    }
}