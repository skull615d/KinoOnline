package com.ldev.kinoonline.feature.player.service

import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
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

    private val managerNotification by inject<PlayerNotificationManager>()
    private var mMediaSession: MediaSessionCompat? = null
    private lateinit var mStateBuilder: PlaybackStateCompat.Builder
    private val mExoPlayer by inject<ExoPlayer>()
    private var oldUri: Uri? = null
    private val mMediaSessionCallback = object : MediaSessionCompat.Callback() {
        override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
            super.onPlayFromUri(uri, extras)
            uri?.let {
                if (uri != oldUri)
                    play(uri)
                else play() // this song was paused so we don't need to reload it
                oldUri = uri
            }
        }


        override fun onPause() {
            super.onPause()
            pause()
        }

        override fun onStop() {
            super.onStop()
            stop()
        }
    }

    override fun onCreate() {
        super.onCreate()
        managerNotification.setPlayer(mExoPlayer)
        mMediaSession = MediaSessionCompat(baseContext, "tag for debugging").apply {
            // Enable callbacks from MediaButtons and TransportControls
            setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )
            // Set initial PlaybackState with ACTION_PLAY, so media buttons can start the player
            mStateBuilder = PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)
            setPlaybackState(mStateBuilder.build())

            // methods that handle callbacks from a media controller
            setCallback(mMediaSessionCallback)

            // Set the session's token so that client activities can communicate with it
            setSessionToken(sessionToken)
            isActive = true
        }
    }

    private fun play(uri: Uri) {
        mExoPlayer.apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            play()
        }
    }

    private fun play() {
        mExoPlayer.apply {
            playWhenReady = true
            updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)
            mMediaSession?.isActive = true
        }
    }

    private fun pause() {
        mExoPlayer.apply {
            playWhenReady = false
            if (playbackState == PlaybackStateCompat.STATE_PLAYING) {
                updatePlaybackState(PlaybackStateCompat.STATE_PAUSED)
            }
        }
    }

    private fun stop() {
        // release the resources when the service is destroyed
        mExoPlayer.playWhenReady = false
        mExoPlayer.release()
        updatePlaybackState(PlaybackStateCompat.STATE_NONE)
        mMediaSession?.isActive = false
        mMediaSession?.release()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot("", null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {

    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }

    private fun updatePlaybackState(state: Int) {
        // You need to change the state because the action taken in the controller depends on the state !!!
        mMediaSession?.setPlaybackState(
            PlaybackStateCompat.Builder().setState(
                state // this state is handled in the media controller
                , 0L, 1.0f // Speed playing
            ).build()
        )
    }

    override fun onBind(intent: Intent?): IBinder {
        return PlayerServiceBinder()
    }


    inner class PlayerServiceBinder : Binder() {
        fun getPlayer() = mExoPlayer
    }
}