package com.ldev.kinoonline.feature.main_screen.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ldev.kinoonline.databinding.ItemMoveMainBinding
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

class MoviesAdapter() : RecyclerView.Adapter<MoviesAdapter.ItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem.title == newItem.title
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.ItemViewHolder {
        val binding =
            ItemMoveMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ItemViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    inner class ItemViewHolder(private val binding: ItemMoveMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Movie){
                binding.tvTitle.text = item.title
            }
    }
}