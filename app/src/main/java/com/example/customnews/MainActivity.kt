package com.example.customnews

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.customnews.data.Post
import com.example.customnews.services.JsonPlaceHolderApi
import com.example.customnews.ui.main.MyPagerAdapter
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        val pagerAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        tabs.setupWithViewPager(viewPager)

    }

}

