package com.ldev.kinoonline.feature.player.service

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import org.koin.android.ext.android.inject

class PlayerService : MediaBrowserServiceCompat() {

    companion object {
        const val MOVIE_NAME = "movieName"
        const val KEY_MOVIE_URL = "movieUrl"
        const val PLAY_PAUSE_ACTION = "PlayPauseAction"
        const val NOTIFICATION_ID = 1231
        const val CHANNEL_ID = "playerChannelId"
        lateinit var movieName: String
        fun newInstance() = PlayerService()
    }

    private val exoPlayer by inject<ExoPlayer>()
    private val manager by inject<PlayerNotificationManager>()
    private var isReady = true
    private var movieUrl: String = ""
    private var playPosition = 0L

    override fun onBind(intent: Intent?): IBinder {
        movieName = intent?.getStringExtra(MOVIE_NAME) ?: ""
        val movieUrlNew = intent?.getStringExtra(KEY_MOVIE_URL)

        exoPlayer.apply {
            if (movieUrlNew != movieUrl) {
                movieUrl = movieUrlNew ?: ""
                setMediaItem(MediaItem.fromUri(movieUrl))
            }
            playWhenReady = isReady
            seekTo(playPosition)
            prepare()
        }
        return PlayerServiceBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        stopSelf()
        return super.onUnbind(intent)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        1
        return BrowserRoot(clientPackageName, rootHints)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        1
    }


    override fun onCreate() {
        super.onCreate()
        manager.setPlayer(exoPlayer)
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        /*exoPlayer.run {
            isReady = playWhenReady
            playPosition = currentPosition
            //mediaItem = currentMediaItem ?: MediaItem.EMPTY
        }*/
    }

    inner class PlayerServiceBinder : Binder() {
        fun getPlayer() = exoPlayer
    }
}