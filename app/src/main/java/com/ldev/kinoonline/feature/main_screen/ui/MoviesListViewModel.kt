package com.ldev.kinoonline.feature.main_screen.ui

import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.ldev.kinoonline.feature.base.constants.Constants
import com.ldev.kinoonline.feature.base.navigation.Screens
import com.ldev.kinoonline.feature.base.view_model.BaseViewModel
import com.ldev.kinoonline.feature.base.view_model.Event
import com.ldev.kinoonline.feature.base.view_model.SingleLiveEvent
import com.ldev.kinoonline.feature.base.view_model.Sorting
import com.ldev.kinoonline.feature.main_screen.domain.MoviesInteractor
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoviesListViewModel(private val interactor: MoviesInteractor, private val router: Router) :
    BaseViewModel<ViewState>() {

    companion object {
        const val COLUMN_MAX = 3
        const val COLUMN_MIN = 1
    }

    init {
        viewModelScope.launch {
            while (true) {
                processDataEvent(DataEvent.GetMovies)
                delay(Constants.GET_MOVIES_DELAY)
            }
        }
    }

    val singleLiveEvent = SingleLiveEvent<SingleEvent>()

    override fun initialViewState(): ViewState {
        return ViewState(emptyList(), true, null, 1, sorted = null)
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
                    movies = sortedMovies(previousState.sorted, event.movies),
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
                return if (previousState.column < COLUMN_MAX) {
                    previousState.copy(column = previousState.column + 1)
                } else {
                    previousState.copy(column = COLUMN_MIN)
                }
            }
            is UiEvent.OnSortClick -> {
                singleLiveEvent.value = SingleEvent.ShowPopupMenu
            }
            is UiEvent.OnSortDateClick -> {
                return previousState.copy(
                    sorted = SortedBy.Date,
                    movies = sortedMovies(SortedBy.Date, previousState.movies)
                )
            }
            is UiEvent.OnSortNameClick -> {
                return previousState.copy(
                    sorted = SortedBy.Name,
                    movies = sortedMovies(SortedBy.Name, previousState.movies)
                )
            }
            is UiEvent.OnSortPopularityClick -> {
                return previousState.copy(
                    sorted = SortedBy.Popularity,
                    movies = sortedMovies(SortedBy.Popularity, previousState.movies)
                )
            }
            is UiEvent.OnSortRatingClick -> {
                return previousState.copy(
                    sorted = SortedBy.Rating,
                    movies = sortedMovies(SortedBy.Rating, previousState.movies)
                )
            }
        }
        return null
    }

    private fun sortedMovies(sorting: Sorting?, list: List<Movie>): List<Movie> {
        return when (sorting) {
            is SortedBy.Date -> {
                list.sortedByDescending { it.releaseDate }
            }
            is SortedBy.Name -> {
                list.sortedBy { it.title }
            }
            is SortedBy.Popularity -> {
                list.sortedByDescending { it.popularity }
            }
            is SortedBy.Rating -> {
                list.sortedByDescending { it.voteAverage }
            }
            else -> list
        }
    }
}