package com.example.customnews.services

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.customnews.R
import com.example.customnews.ui.main.SearchResults

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            val intentRec = Intent(context, SearchResults::class.java)
            intentRec.putExtra(isNotification, 1)
            val pendingIntent = PendingIntent.getActivity(context, 1, intentRec, 0)
            val notificationBuilder = NotificationCompat.Builder(
                context, "1"
            )
                .setSmallIcon(R.drawable.ic_search_black_24dp)
                .setContentTitle("News updates")
                .setContentText("Check recent news matching your search criteria")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(context)) {
                notify(1, notificationBuilder.build())
            }
        }
    }

}