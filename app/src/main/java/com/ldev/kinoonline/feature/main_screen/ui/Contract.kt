package com.ldev.kinoonline.feature.main_screen.ui

import com.ldev.kinoonline.feature.base.Event
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

data class ViewState(
    val movies: List<Movie>,
    val isLoading: Boolean,
    val errorMessage: String?
)

sealed class UiEvent : Event {
    object GetMovies : UiEvent()
}

sealed class DataEvent: Event{
    data class SuccessMoviesRequest(val movies: List<Movie>) : DataEvent()
    data class ErrorMoviesRequest(val errorMessage: String?) : DataEvent()
}