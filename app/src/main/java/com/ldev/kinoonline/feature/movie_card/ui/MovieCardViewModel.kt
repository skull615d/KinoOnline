package com.ldev.kinoonline.feature.movie_card.ui

import com.github.terrakok.cicerone.Router
import com.ldev.kinoonline.feature.base.navigation.Screens
import com.ldev.kinoonline.feature.base.view_model.BaseViewModel
import com.ldev.kinoonline.feature.base.view_model.Event

class MovieCardViewModel(private val router: Router) : BaseViewModel<ViewState>() {

    override fun initialViewState() = ViewState(null, emptyList())

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnPlayClick -> {
                router.navigateTo(Screens.player(event.movie.video, event.movie.title))
            }
            is UiEvent.OnBackClicked -> {
                router.exit()
            }
            is UiEvent.OnMovieCardClick -> {
                //TODO
            }
        }
        return null
    }
}