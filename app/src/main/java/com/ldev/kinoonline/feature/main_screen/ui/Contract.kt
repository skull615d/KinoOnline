package com.ldev.kinoonline.feature.main_screen.ui

import com.ldev.kinoonline.feature.base.view_model.Event
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

data class ViewState(
    val movies: List<Movie>,
    val isLoading: Boolean,
    val errorMessage: String?
)

sealed class UiEvent : Event {
    data class OnCardMovieClick(val movie: Movie) : UiEvent()
}

sealed class DataEvent : Event {
    object GetMovies : DataEvent()
    data class SuccessMoviesRequest(val movies: List<Movie>) : DataEvent()
    data class ErrorMoviesRequest(val errorMessage: String?) : DataEvent()
}

sealed class SingleEvent : Event {
    data class OpenMovieCard(val movie: Movie, val similarMovies: List<Movie>) : SingleEvent()
}