package com.ldev.kinoonline.feature.player.ui

import com.github.terrakok.cicerone.Router
import com.ldev.kinoonline.feature.base.view_model.BaseViewModel
import com.ldev.kinoonline.feature.base.view_model.Event

class PlayerFragmentViewModel(private val router: Router) : BaseViewModel<ViewState>() {

    override fun initialViewState() = ViewState("")

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnBackPressed -> {
                router.exit()
            }
        }
        return null
    }
}