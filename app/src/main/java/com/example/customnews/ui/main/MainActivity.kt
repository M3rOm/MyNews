package com.example.customnews.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
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

        toolbar_activity_search.title = getString(R.string.app_name)
        setSupportActionBar(toolbar_activity_search)

        val pagerAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        tabs.setupWithViewPager(viewPager)
        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
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

