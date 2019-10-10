package com.example.customnews.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.customnews.R
import kotlinx.android.synthetic.main.activity_main.*


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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflating the menu
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //Handling action bar item clicks here
        when (item?.itemId) {
        R.id.search_icon -> openSearchActivity()
            R.id.destination_notifications -> openNotificationsActivity()
            R.id.destination_help -> Toast.makeText(this, "Help is on the way!", Toast.LENGTH_SHORT).show()
            R.id.destination_about -> Toast.makeText(this, "Thank you for your interests!", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openNotificationsActivity() {
        intent = Intent(this, NotificationActivity::class.java)
        startActivity(intent)
    }

    private fun openSearchActivity() {
        intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }


}

