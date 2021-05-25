package com.sibelyildiz.androidnotificationsample

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.sibelyildiz.androidnotification.MainActivity
import com.sibelyildiz.androidnotification.R

class AlarmReceiver : BroadcastReceiver() {
    private val NOTIFICATION_ID = 100
    private val REQUEST_CODE = 0

    override fun onReceive(context: Context, intent: Intent?) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
                context,
                REQUEST_CODE,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notification Title")
                .setContentText("Notification Content")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentPendingIntent)

        val notificationManager =
                ContextCompat.getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(NOTIFICATION_ID, builder.build())

    }
}