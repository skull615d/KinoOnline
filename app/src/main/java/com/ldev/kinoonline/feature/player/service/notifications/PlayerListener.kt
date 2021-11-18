package com.ldev.kinoonline.feature.player.service.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.ldev.kinoonline.feature.player.service.PlayerService

class PlayerListener(private val context: Context) :
    PlayerNotificationManager.NotificationListener {

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {

        NotificationManagerCompat.from(context).apply {
            val notificationBuilder = NotificationCompat.Builder(context, notification).apply {
                setContentTitle(PlayerService.movieName)
                setContentText("Movie")
                setOngoing(ongoing)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(getChannel())
            }
            notify(notificationId, notificationBuilder.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getChannel() = NotificationChannel(
        PlayerService.CHANNEL_ID,
        "Player",
        NotificationManager.IMPORTANCE_DEFAULT
    )
}