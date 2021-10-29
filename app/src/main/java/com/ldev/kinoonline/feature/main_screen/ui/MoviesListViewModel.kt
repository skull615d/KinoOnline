package com.ldev.kinoonline.feature.main_screen.ui

import android.provider.ContactsContract
import com.ldev.kinoonline.feature.base.BaseViewModel
import com.ldev.kinoonline.feature.base.Event
import com.ldev.kinoonline.feature.main_screen.domain.MoviesInteractor

class MoviesListViewModel(private val interactor: MoviesInteractor) : BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState {
        return ViewState(emptyList(), true, "")
    }


    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.GetMovies -> {
                interactor.getMovies().fold(
                    onSuccess = {
                        processDataEvent(DataEvent.SuccessMoviesRequest(it.results))
                    },
                    onError = {
                        processDataEvent(DataEvent.ErrorMoviesRequest(it.localizedMessage ?: ""))
                    }
                )
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