package com.ldev.kinoonline.feature.main_screen.ui

import com.github.terrakok.cicerone.Router
import com.ldev.kinoonline.feature.base.BaseViewModel
import com.ldev.kinoonline.feature.base.Event
import com.ldev.kinoonline.feature.base.Screens
import com.ldev.kinoonline.feature.base.SingleLiveEvent
import com.ldev.kinoonline.feature.main_screen.domain.MoviesInteractor

class MoviesListViewModel(private val interactor: MoviesInteractor, private val router: Router) :
    BaseViewModel<ViewState>() {

    val singleLiveEvent = SingleLiveEvent<SingleEvent>()

    init {
        processUiEvent(DataEvent.GetMovies)
    }

    override fun initialViewState(): ViewState {
        return ViewState(emptyList(), true, "")
    }


    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is DataEvent.GetMovies -> {
                interactor.getMovies().fold(
                    onSuccess = {
                        processDataEvent(DataEvent.SuccessMoviesRequest(it.results))
                    },
                    onError = {
                        processDataEvent(DataEvent.ErrorMoviesRequest(it.localizedMessage ?: ""))
                    }
                )
            }
            is UiEvent.OnCardMovieClick -> {
                router.navigateTo(Screens.movieCard(event.movie, previousState.movies))
                //singleLiveEvent.value = SingleEvent.OpenMovieCard(event.movie, previousState.movies)
            }
            is DataEvent.SuccessMoviesRequest -> {
                return previousState.copy(
                    movies = event.movies,
                    isLoading = false
                )
            }
            is DataEvent.ErrorMoviesRequest -> {
                return previousState.copy(
                    isLoading = false,
                    errorMessage = event.errorMessage
                )
            }
        }
        return null
    }
}