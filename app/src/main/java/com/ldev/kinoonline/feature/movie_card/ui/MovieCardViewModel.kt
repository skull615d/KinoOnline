package com.ldev.kinoonline.feature.movie_card.ui

import com.github.terrakok.cicerone.Router
import com.ldev.kinoonline.feature.base.BaseViewModel
import com.ldev.kinoonline.feature.base.Event
import com.ldev.kinoonline.feature.base.Screens
import com.ldev.kinoonline.feature.base.SingleLiveEvent

class MovieCardViewModel(private val router: Router) : BaseViewModel<ViewState>() {

    val singleLiveEvent = SingleLiveEvent<SingleEvent>()

    override fun initialViewState() = ViewState(null, emptyList())

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnPlayClick -> {
                router.navigateTo(Screens.player(event.movie.video, event.movie.title))
            }
            is UiEvent.OnMovieCardClick -> {
                //TODO
            }
        }
        return null
    }
}