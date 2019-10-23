package com.example.customnews.ui.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.customnews.R
import com.example.customnews.services.AlertReceiver
import kotlinx.android.synthetic.main.notifications.*
import org.json.JSONObject

class NotificationActivity : AppCompatActivity() {

    private lateinit var myAlarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notifications)
        //Initialize toolbar
        toolbar_activity_notifications.title = getString(R.string.search_activity_name)
        //Setting the Alarm
        myAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
        //Called when user switches on notifications
        switch1.setOnClickListener {
            if (switch1.isChecked) {
                if (validateSearchCriteria()) {
                    startNotificationSchedule()
                    Toast.makeText(this, "Daily notification is on", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please check search conditions", Toast.LENGTH_SHORT)
                        .show()
                    switch1.isChecked = false
                }
            } else {
                stopNotificationSchedule()
                Toast.makeText(this, "Daily notification is off", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun startNotificationSchedule() {
        //Initialize shared preference handle
        val sharedPref = getSharedPreferences(SEARCH, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val notificationPref = emptyMap<String, String>().toMutableMap()

        notificationPref["expressions"] = notification_search_field.text.toString()
        notificationPref["locations"] = formatter(doSearchIn())

        val jsonData = JSONObject(notificationPref.toString()).toString()
        editor.putString("notificationData", jsonData)
        editor.apply()
        //val intent = Intent(this, AlertReceiver::class.java)
        myAlarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME,
            5000,
            //SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,
            //AlarmManager.INTERVAL_DAY,
            //TODO("Clear test values")
            60000,
            pendingIntent
        )
    }

    //Creates the lucene compatible field grouping string for the api search
    private fun formatter(searchLocation: ArrayList<String?>): String {
        var result = "news_desk:(${searchLocation[0]}"
        for (item in searchLocation) {
            item?.let {
                result += " || $item"
            }
        }
        result += ")"
        return result
    }

    private fun validateSearchCriteria(): Boolean {
        return (validateSearchField() && validateCheckBox())
    }

    private fun validateCheckBox(): Boolean {
        return if (!(notification_checkBox1.isChecked ||
                    notification_checkBox2.isChecked ||
                    notification_checkBox3.isChecked ||
                    notification_checkBox4.isChecked ||
                    notification_checkBox5.isChecked ||
                    notification_checkbox6.isChecked)
        ) {
            Toast.makeText(this, "Check at least one topic", Toast.LENGTH_SHORT).show()
            false
        } else true
    }

    private fun validateSearchField(): Boolean {
        return if (notification_search_field.text.toString().isBlank()) {
            Toast.makeText(this, "Please enter a search expression", Toast.LENGTH_SHORT).show()
            false
        } else true
    }

    //This creates an array list of Strings populated by the topics which the user wants to search in
    private fun doSearchIn(): ArrayList<String?> {
        val technology = notification_checkBox1.text.toString()
        val business = notification_checkBox2.text.toString()
        val environment = notification_checkBox3.text.toString()
        val movies = notification_checkBox4.text.toString()
        val politics = notification_checkBox5.text.toString()
        val classifieds = notification_checkbox6.text.toString()
        val searchIn = arrayListOf<String?>()
        if (notification_checkBox1.isChecked) searchIn += environment
        if (notification_checkBox2.isChecked) searchIn += technology
        if (notification_checkBox3.isChecked) searchIn += movies
        if (notification_checkBox4.isChecked) searchIn += business
        if (notification_checkBox5.isChecked) searchIn += politics
        if (notification_checkbox6.isChecked) searchIn += classifieds
        return searchIn
    }

    private fun stopNotificationSchedule() {
        myAlarmManager.cancel(pendingIntent)
    }
}