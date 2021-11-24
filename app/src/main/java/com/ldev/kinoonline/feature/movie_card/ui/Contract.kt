package com.ldev.kinoonline.feature.movie_card.ui

import com.ldev.kinoonline.feature.base.view_model.Event
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

data class ViewState(
    val movie: Movie?,
    val similarMovies: List<Movie>
)

sealed class UiEvent : Event {
    data class OnPlayClick(val movie: Movie) : UiEvent()
    data class OnMovieCardClick(val movie: Movie) : UiEvent()
    object OnBackClicked : UiEvent()
}

sealed class SingleEvent : Event {
    data class OpenPlayer(val movieUrl: String) : SingleEvent()
    data class OpenMovieCard(val movie: Movie) : SingleEvent()
}