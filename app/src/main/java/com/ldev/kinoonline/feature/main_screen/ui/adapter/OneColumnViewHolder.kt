package com.ldev.kinoonline.feature.main_screen.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ldev.kinoonline.databinding.ItemMovieOneColumnBinding
import com.ldev.kinoonline.feature.base.loadImage
import com.ldev.kinoonline.feature.base.setThrottledClickListener
import com.ldev.kinoonline.feature.base.toStringFormat
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

class OneColumnViewHolder(private val binding: ItemMovieOneColumnBinding) :
    RecyclerView.ViewHolder(binding.root), ViewHolder {
    constructor(parent: ViewGroup) : this(
        ItemMovieOneColumnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun bind(item: Movie, onMovieClick: (Movie) -> Unit) {

        binding.apply {
            cardView.setThrottledClickListener { onMovieClick(item) }
            tvYear.text = item.releaseDate?.toStringFormat("yyyy") ?: ""
            tvVoteAverage.text = item.voteAverage.toString()
            tvDescription.text = item.overview
            ivMovieImage.loadImage(item.posterPath)
            tvName.text = item.title
        }
    }
}