package com.ldev.kinoonline.feature.player.ui

import com.google.android.exoplayer2.MediaItem
import com.ldev.kinoonline.feature.base.Event

data class ViewState(
    val mediaItem: MediaItem?,
    val playWhenReady: Boolean,
    val playbackPosition: Long
)

sealed class UiEvent : Event {
    data class OnSetMediaItem(val mediaItem: MediaItem) : UiEvent()

    data class OnSaveDataPlayer(
        val playWhenReady: Boolean,
        val mediaItem: MediaItem?,
        val playbackPosition: Long
    ) : UiEvent()
}

sealed class DataEvent : Event