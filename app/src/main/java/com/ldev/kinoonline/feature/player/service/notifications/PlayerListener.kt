package com.ldev.kinoonline.feature.player.service.notifications

import android.content.Context
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class PlayerListener(private val context: Context) :
    PlayerNotificationManager.NotificationListener {

   /* override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
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
    )*/
}