package com.ldev.kinoonline.feature.player.service

import android.app.*
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.ldev.kinoonline.R
import com.ldev.kinoonline.feature.player.service.notifications.PlayerListener
import com.ldev.kinoonline.feature.player.service.notifications.PlayerNotificationActivity
import org.koin.android.ext.android.inject

class PlayerService : Service() {

    companion object {
        const val MOVIE_NAME = "movieName"
        const val KEY_MOVIE_URL = "movieUrl"
        const val PLAY_PAUSE_ACTION = "PlayPauseAction"
        const val NOTIFICATION_ID = 1231
        const val CHANNEL_ID = "playerChannelId"
        const val REQUEST_CODE = 12
    }

    private val exoPlayer by inject<ExoPlayer>()
    private val managerBuilder by inject<PlayerNotificationManager.Builder>()
    private var isReady = true
    private var movieName: String = ""
    private lateinit var manager: PlayerNotificationManager
    private val contentIntent by lazy {
        PendingIntent.getActivity(
            applicationContext,
            REQUEST_CODE,
            Intent(applicationContext, PlayerNotificationActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
    }
    private val playerListener: PlayerListener by lazy {
        PlayerListener(this)
    }

    private val playerNotificationListener =
        object : PlayerNotificationManager.NotificationListener {
            override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                super.onNotificationCancelled(notificationId, dismissedByUser)
                stopSelf()
                stopForeground(true)
            }

            override fun onNotificationPosted(
                notificationId: Int,
                notification: Notification,
                ongoing: Boolean
            ) {
                NotificationManagerCompat.from(applicationContext).apply {
                    val notificationBuilder =
                        NotificationCompat.Builder(applicationContext, notification).apply {
                            setContentTitle(movieName)
                            setContentText(getString(R.string.movie))
                            setOngoing(ongoing)
                            setContentIntent(contentIntent)
                        }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        createNotificationChannel(getChannel())
                        startForeground(notificationId, notificationBuilder.build())
                    }
                    notify(NOTIFICATION_ID, notificationBuilder.build())
                }
            }
        }

    override fun onBind(intent: Intent?): IBinder {
        movieName = intent?.getStringExtra(MOVIE_NAME) ?: ""
        val movieUrlNew = intent?.getStringExtra(KEY_MOVIE_URL) ?: ""

        exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(movieUrlNew))
            playWhenReady = isReady
            addListener(playerListener)
            prepare()
        }
        return PlayerServiceBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        manager = managerBuilder.setNotificationListener(playerNotificationListener).build()
        manager.setPlayer(exoPlayer)
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        manager.setPlayer(null)
    }

    inner class PlayerServiceBinder : Binder() {
        fun getPlayer() = exoPlayer
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getChannel() = NotificationChannel(
        CHANNEL_ID,
        getString(R.string.channel_name),
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply { setSound(null, null) }
}