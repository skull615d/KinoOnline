package com.ldev.kinoonline.feature.player.di

import com.google.android.exoplayer2.ExoPlayer
import com.ldev.kinoonline.feature.player.ui.PlayerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modulePlayer = module {
    single<ExoPlayer> {
        ExoPlayer.Builder(androidContext()).build()
    }
    viewModel {
        PlayerViewModel(get<ExoPlayer>())
    }
}