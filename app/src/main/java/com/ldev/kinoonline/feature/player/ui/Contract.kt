package com.ldev.kinoonline.feature.player.ui

import com.ldev.kinoonline.feature.base.view_model.Event

data class ViewState(
    val url: String
)

sealed class UiEvent : Event {
    object OnBackPressed : UiEvent()
}

sealed class DataEvent : Event {
    data class OnPlayPressed(val url: String) : DataEvent()
}