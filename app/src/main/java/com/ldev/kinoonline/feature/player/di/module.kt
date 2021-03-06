package com.ldev.kinoonline.feature.player.di

import com.github.terrakok.cicerone.Router
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.ldev.kinoonline.feature.player.service.PlayerService
import com.ldev.kinoonline.feature.player.service.notifications.PlayerAdapter
import com.ldev.kinoonline.feature.player.ui.PlayerFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modulePlayer = module {

    factory<ExoPlayer> {
        ExoPlayer.Builder(androidContext()).build()
    }

    single<PlayerAdapter> {
        PlayerAdapter(androidContext())
    }

    single<PlayerNotificationManager.Builder> {
        PlayerNotificationManager.Builder(
            androidContext(),
            PlayerService.NOTIFICATION_ID,
            PlayerService.CHANNEL_ID
        ).apply {
            setMediaDescriptionAdapter(get<PlayerAdapter>())
        }
    }

    viewModel<PlayerFragmentViewModel> {
        PlayerFragmentViewModel(get<Router>())
    }
}