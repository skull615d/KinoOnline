package com.ldev.kinoonline.feature.main_screen.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

class DiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem == newItem
}