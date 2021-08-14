package com.susheelkaram.trackky.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.susheelkaram.myread.R
import kotlin.random.Random


/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
class Notify(private val mContext: Context) {
//    var settingsUtil = SettingsUtil(mContext)

    fun showSimpleNotification(title: String, body: String) {

        var notification = createSimpleNotificationBuilder(title, body).build()

        with(NotificationManagerCompat.from(mContext)) {
            notify(Random.nextInt(1, 100), notification)
        }
    }

    fun showSimpleNotificationWithIntent(title: String, body: String, intent: PendingIntent) {

        var notification = createSimpleNotificationBuilder(title, body)
            .setContentIntent(intent).build()

        with(NotificationManagerCompat.from(mContext)) {
            notify(Random.nextInt(1, 100), notification)
        }
    }


    fun createSimpleNotificationBuilder(title: String, body: String): NotificationCompat.Builder {
        createNotificationChannel()

//        val isVibrationEnabled : Boolean = settingsUtil.isVibrationEnabled()

        val channelId = mContext.getString(R.string.channel_id_feed_notitifications)

        return NotificationCompat.Builder(mContext, channelId)
            .setSmallIcon(R.drawable.reeder_logo_transparent)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setVibrate(if(isVibrationEnabled) longArrayOf(1000, 1000) else longArrayOf())
    }

    private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = mContext.getString(R.string.channel_id_feed_notitifications)
                val mChannel = NotificationChannel(
                    channelId,
                    "General Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                mChannel.description = "This is default channel used for all other notifications"

                val notificationManager =
                    mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(mChannel)
            }
        }
    }