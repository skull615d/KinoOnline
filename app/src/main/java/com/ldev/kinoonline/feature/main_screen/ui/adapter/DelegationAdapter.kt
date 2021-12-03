package com.ldev.kinoonline.feature.main_screen.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.ldev.kinoonline.databinding.ItemMovieThreeColumnBinding
import com.ldev.kinoonline.feature.base.loadImage
import com.ldev.kinoonline.feature.base.setThrottledClickListener
import com.ldev.kinoonline.feature.base.toStringFormat
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

fun moviesAdapterDelegate(itemClickedListener: (Movie) -> Unit) =
    adapterDelegateViewBinding<Movie, Movie, ItemMovieThreeColumnBinding>(
        { layoutInflater, root -> ItemMovieThreeColumnBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.cardView.setThrottledClickListener {
            itemClickedListener(item)
        }
        bind {
            binding.apply {
                tvYear.text = item.releaseDate?.toStringFormat("yyyy") ?: ""
                tvVoteAverage.text = item.voteAverage.toString()
                ivMovieImage.loadImage(item.posterPath)
            }
        }
    }


