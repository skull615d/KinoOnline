package com.ldev.kinoonline.feature.main_screen.ui.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.ldev.kinoonline.databinding.ItemMovieMainBinding
import com.ldev.kinoonline.feature.base.loadImage
import com.ldev.kinoonline.feature.base.toStringFormat
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

fun moviesAdapterDelegate(itemClickedListener: (Movie) -> Unit) =
    adapterDelegateViewBinding<Movie, Movie, ItemMovieMainBinding>(
        { layoutInflater, root -> ItemMovieMainBinding.inflate(layoutInflater, root, false) }
    ) {
        binding.cardView.setOnClickListener {
            itemClickedListener(item)
        }
        bind {
            binding.apply {
                tvDescription.text = item.releaseDate?.toStringFormat("yyyy") ?: ""
                tvVoteAverage.text = item.voteAverage.toString()
                ivMovieImage.loadImage(item.posterPath)
            }
        }
    }