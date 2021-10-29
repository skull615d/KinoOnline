package com.ldev.kinoonline.feature.main_screen.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ldev.kinoonline.R
import com.ldev.kinoonline.databinding.FragmentMoviesListBinding
import com.ldev.kinoonline.feature.main_screen.ui.adapter.MoviesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment: Fragment(R.layout.fragment_movies_list) {

    private val viewModel by viewModel<MoviesListViewModel>()
    private val binding by viewBinding(FragmentMoviesListBinding::bind)
    private val moviesAdapter = MoviesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = moviesAdapter
        }
        viewModel.processUiEvent(UiEvent.GetMovies)
        viewModel.viewState.observe(viewLifecycleOwner,::render)
    }
    private fun render(viewState: ViewState){
        moviesAdapter.differ.submitList(viewState.movies)
    }
}