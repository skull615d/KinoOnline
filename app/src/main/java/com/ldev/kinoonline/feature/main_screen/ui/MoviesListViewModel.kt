package com.ldev.kinoonline.feature.main_screen.ui

import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.ldev.kinoonline.feature.base.constants.Constants
import com.ldev.kinoonline.feature.base.navigation.Screens
import com.ldev.kinoonline.feature.base.view_model.BaseViewModel
import com.ldev.kinoonline.feature.base.view_model.Event
import com.ldev.kinoonline.feature.main_screen.domain.MoviesInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoviesListViewModel(private val interactor: MoviesInteractor, private val router: Router) :
    BaseViewModel<ViewState>() {

    private val columnMax = 3
    private val columnMin = 1

    init {
        viewModelScope.launch {
            while (true) {
                delay(Constants.GET_MOVIES_DELAY)
                processDataEvent(DataEvent.GetMovies)
            }
        }
    }

    override fun initialViewState(): ViewState {
        return ViewState(emptyList(), true, null, 1)
    }


    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.GetMovies -> {
                processDataEvent(DataEvent.LoadData(true))
                interactor.getMovies().fold(
                    onSuccess = {
                        processDataEvent(DataEvent.SuccessMoviesRequest(it.results))
                    },
                    onError = {
                        processDataEvent(DataEvent.ErrorMoviesRequest(it.localizedMessage ?: ""))
                    }
                )
            }
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
            }
            is DataEvent.SuccessMoviesRequest -> {
                return previousState.copy(
                    movies = event.movies,
                    isLoading = false,
                    errorMessage = null
                )
            }
            is DataEvent.ErrorMoviesRequest -> {
                return previousState.copy(
                    isLoading = false,
                    errorMessage = event.errorMessage
                )
            }
            is DataEvent.LoadData -> {
                return previousState.copy(
                    isLoading = event.isLoading
                )
            }
            is UiEvent.OnChangeGridClick -> {
                return if (previousState.column < columnMax) {
                    previousState.copy(column = previousState.column + 1)
                } else {
                    previousState.copy(column = columnMin)
                }
            }
        }
        return null
    }
}