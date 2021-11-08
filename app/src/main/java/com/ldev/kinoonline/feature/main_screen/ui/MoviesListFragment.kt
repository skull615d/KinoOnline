package com.ldev.kinoonline.feature.main_screen.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.ldev.kinoonline.R
import com.ldev.kinoonline.databinding.FragmentMoviesListBinding
import com.ldev.kinoonline.feature.base.updateList
import com.ldev.kinoonline.feature.main_screen.ui.adapter.moviesAdapterDelegate
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {
    companion object {
        fun newInstance() = MoviesListFragment()
    }

    private val viewModel by viewModel<MoviesListViewModel>()
    private val binding by viewBinding(FragmentMoviesListBinding::bind)
    private val moviesAdapter = ListDelegationAdapter(
        moviesAdapterDelegate { viewModel.processUiEvent(UiEvent.OnCardMovieClick(it)) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = moviesAdapter
        }
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    private fun render(viewState: ViewState) {
        moviesAdapter.updateList(viewState.movies)
    }
}