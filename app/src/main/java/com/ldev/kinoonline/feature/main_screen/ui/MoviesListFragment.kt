package com.ldev.kinoonline.feature.main_screen.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.ldev.kinoonline.R
import com.ldev.kinoonline.databinding.FragmentMoviesListBinding
import com.ldev.kinoonline.feature.base.updateList
import com.ldev.kinoonline.feature.main_screen.ui.adapter.moviesAdapterDelegate
import com.ldev.kinoonline.feature.movie_card.ui.MovieCardFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

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
        viewModel.processUiEvent(UiEvent.GetMovies)
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
        viewModel.singleLiveEvent.observe(viewLifecycleOwner, ::onSingleEvent)
    }

    private fun render(viewState: ViewState) {
        moviesAdapter.updateList(viewState.movies)
    }

    private fun onSingleEvent(event: SingleEvent) {
        when (event) {
            is SingleEvent.OpenMovieCard -> {
                parentFragmentManager.commit {
                    addToBackStack("movies")
                    setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
                    add(
                        R.id.mainContainer,
                        MovieCardFragment.newInstance(event.movie, event.similarMovies)
                    )
                }
            }
        }
    }
}