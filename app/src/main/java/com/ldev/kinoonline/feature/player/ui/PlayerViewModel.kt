package com.ldev.kinoonline.feature.player.ui

import com.google.android.exoplayer2.ExoPlayer
import com.ldev.kinoonline.feature.base.BaseViewModel
import com.ldev.kinoonline.feature.base.Event

class PlayerViewModel(private val exoPlayer: ExoPlayer) : BaseViewModel<ViewState>() {

    override fun initialViewState() = ViewState(
        player = exoPlayer
    )

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnSetMediaItem -> {
                if (exoPlayer.currentMediaItem != event.mediaItem) {
                    exoPlayer.setMediaItem(event.mediaItem)
                    return previousState.copy(
                        player = exoPlayer
                    )
                }
            }
            is UiEvent.OnDestroyFragment -> {
                return previousState.copy(
                    player = null
                )
            }
        }
        return null
    }
}