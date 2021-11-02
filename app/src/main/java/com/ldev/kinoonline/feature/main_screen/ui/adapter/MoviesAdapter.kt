package com.ldev.kinoonline.feature.main_screen.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.ldev.kinoonline.databinding.ItemMovieMainBinding
import com.ldev.kinoonline.feature.base.loadImage
import com.ldev.kinoonline.feature.base.toStringFormat
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

class MoviesAdapter(
    private val onMovieCardClick: (movie: Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ItemViewHolder>() {

    val differ = AsyncListDiffer(this, DiffCallback())

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.ItemViewHolder {
        val binding =
            ItemMovieMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ItemViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    inner class ItemViewHolder(private val binding: ItemMovieMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            binding.apply {
                tvDescription.text = item.releaseDate?.toStringFormat("yyyy") ?: ""
                tvVoteAverage.text = item.voteAverage.toString()
                ivMovieImage.loadImage(item.posterPath)
                cardView.setOnClickListener { onMovieCardClick(item) }
            }
        }

    }
}