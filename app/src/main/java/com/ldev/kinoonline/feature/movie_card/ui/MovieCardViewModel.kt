package com.ldev.kinoonline.feature.movie_card.ui

import com.ldev.kinoonline.feature.base.BaseViewModel
import com.ldev.kinoonline.feature.base.Event
import com.ldev.kinoonline.feature.base.SingleLiveEvent

class MovieCardViewModel : BaseViewModel<ViewState>() {

    val singleLiveEvent = SingleLiveEvent<SingleEvent>()

    override fun initialViewState() = ViewState(null, emptyList())

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnPlayClick -> {
                singleLiveEvent.value = SingleEvent.OpenPlayer(event.movieUrl)
            }
            is UiEvent.OnMovieCardClick -> {
                singleLiveEvent.value = SingleEvent.OpenMovieCard(event.movie)
            }
        }
        return null
    }
}