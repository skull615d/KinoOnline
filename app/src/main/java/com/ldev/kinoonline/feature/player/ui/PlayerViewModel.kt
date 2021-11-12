package com.ldev.kinoonline.feature.player.ui

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.ldev.kinoonline.feature.base.BaseViewModel
import com.ldev.kinoonline.feature.base.Event

class PlayerViewModel(private val exoPlayer: ExoPlayer) : BaseViewModel<ViewState>() {

    override fun initialViewState() = ViewState(
        mediaItem = MediaItem.EMPTY,
        playWhenReady = true,
        playbackPosition = 0L
    )

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnSetMediaItem -> {
                return previousState.copy(
                    mediaItem = event.mediaItem
                )
            }
            is UiEvent.OnSaveDataPlayer -> {
                return previousState.copy(
                    mediaItem = event.mediaItem,
                    playWhenReady = event.playWhenReady,
                    playbackPosition = event.playbackPosition
                )
            }
        }
        return null
    }
}