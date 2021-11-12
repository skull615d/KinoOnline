package com.ldev.kinoonline.feature.player.di

import com.google.android.exoplayer2.ExoPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val modulePlayer = module {

    single<ExoPlayer> {
        ExoPlayer.Builder(androidContext()).build()
    }
}