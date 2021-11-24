package com.ldev.kinoonline.feature.player.service.notifications

import android.widget.Toast
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.ldev.kinoonline.R
import com.ldev.kinoonline.feature.player.service.PlayerService

class PlayerListener(private val playerService: PlayerService) : Player.Listener {
    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        Toast.makeText(
            playerService,
            playerService.getString(R.string.loading_error),
            Toast.LENGTH_LONG
        ).show()
    }
}