package com.sibelyildiz.androidnotification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AlarmManagerCompat
import com.sibelyildiz.androidnotificationsample.AlarmReceiver
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createChannel(getString(R.string.channel_id), getString(R.string.channel_name))

        val alarmButton = findViewById<Button>(R.id.btnAlarm)
        alarmButton.setOnClickListener {
            createAlarm()
        }
    }

    private fun createAlarm() {
        val alarmManager: AlarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(this, AlarmReceiver::class.java)
        val notifyPendingIntent = PendingIntent.getBroadcast(
                this,
                REQUEST_CODE,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Add 1 minute to the current time
        val triggerTime = Calendar.getInstance()
        triggerTime.add(Calendar.MINUTE, 1)

        AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                triggerTime.timeInMillis,
                notifyPendingIntent
        )
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setShowBadge(true)
            }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.channel_description)

            val notificationManager = this.getSystemService(
                    NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}