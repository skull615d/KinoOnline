package com.ldev.kinoonline.feature.main_screen.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

class SimpleAdapter(
    private var list: List<Movie>,
    private val layoutManager: GridLayoutManager? = null,
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType {
        ONE_COLUMN,
        TWO_COLUMN,
        THREE_COLUMN
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.ONE_COLUMN.ordinal -> OneColumnViewHolder(parent)
            ViewType.TWO_COLUMN.ordinal -> TwoColumnViewHolder(parent)
            else -> ThreeColumnViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BindedViewHolder).bind(list[position], onMovieClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (layoutManager?.spanCount) {
            1 -> ViewType.ONE_COLUMN.ordinal
            2 -> ViewType.TWO_COLUMN.ordinal
            else -> ViewType.THREE_COLUMN.ordinal
        }
    }

    fun updateArticles(newStates: List<Movie>) {
        list = newStates
        notifyItemRangeChanged(0, this.itemCount)
    }
}