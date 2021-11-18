package com.ldev.kinoonline.feature.player.service.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.ldev.kinoonline.feature.player.service.PlayerService

class PlayerAdapter(private val context: Context) :
    PlayerNotificationManager.MediaDescriptionAdapter {
    override fun getCurrentContentTitle(player: Player): CharSequence {
        return player.mediaMetadata.title.toString()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        return PendingIntent.getService(
            context,
            0,
            Intent(context, PlayerService::class.java).apply {
                putExtra(PlayerService.PLAY_PAUSE_ACTION, 0)
            },
            PendingIntent.FLAG_MUTABLE
        )
    }

    override fun getCurrentContentText(player: Player): CharSequence {
        return player.mediaMetadata.subtitle.toString()
    }

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        return null
    }
}