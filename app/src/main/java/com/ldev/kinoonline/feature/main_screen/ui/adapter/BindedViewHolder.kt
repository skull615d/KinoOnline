package com.ldev.kinoonline.feature.main_screen.ui.adapter

import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

interface BindedViewHolder {
    fun bind(item: Movie, onMovieClick: (Movie) -> Unit)
}