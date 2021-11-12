package com.ldev.kinoonline.feature.player.ui

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.ldev.kinoonline.feature.base.Event

data class ViewState(
    val player: ExoPlayer?
)

sealed class UiEvent : Event {

    data class OnSetMediaItem(val mediaItem: MediaItem) : UiEvent()

    object OnDestroyFragment : UiEvent()
}

sealed class DataEvent : Event