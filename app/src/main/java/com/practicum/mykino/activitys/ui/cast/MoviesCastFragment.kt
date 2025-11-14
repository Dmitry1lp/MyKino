package com.practicum.mykino.activitys.ui.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.practicum.mykino.R
import com.practicum.mykino.activitys.presentation.movies.MoviesCastViewModel
import com.practicum.mykino.activitys.ui.movies.models.MoviesCastState
import com.practicum.mykino.databinding.FragmentMoviesCastBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MoviesCastFragment: Fragment(R.layout.fragment_movies_cast) {

    private val delegates = MoviesCastAdapterDelegates()
    private val adapter = ListDelegationAdapter(
        delegates.movieCastHeaderDelegate(),
        delegates.movieCastPersonDelegate(),
    )
    private lateinit var binding: FragmentMoviesCastBinding
    private val moviesCastViewModel: MoviesCastViewModel by viewModel {
        parametersOf(requireArguments().getString(ARGS_MOVIE_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesCastBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesCastRecyclerView.adapter = adapter
        binding.moviesCastRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        moviesCastViewModel.observeState().observe(viewLifecycleOwner) {
            when (it) {
                is MoviesCastState.Content -> showContent(it)
                is MoviesCastState.Error -> showError(it)
                is MoviesCastState.Loading -> showLoading()
            }
        }
    }

        private fun showLoading() {
            binding.progressBar.isVisible = true
            binding.contentContainer.isVisible = false
            binding.errorMessageTextView.isVisible = false
        }

        private fun showContent(state: MoviesCastState.Content) {
            binding.progressBar.isVisible = false
            binding.contentContainer.isVisible = true
            binding.errorMessageTextView.isVisible = false
            binding.movieTitle.text = state.fullTitle

            adapter.items = state.items
            adapter.notifyDataSetChanged()
        }

        private fun showError(state: MoviesCastState.Error) {
            binding.progressBar.isVisible = false
            binding.contentContainer.isVisible = false
            binding.errorMessageTextView.isVisible = true
            binding.errorMessageTextView.text = state.message
        }
        companion object {

            private const val ARGS_MOVIE_ID = "movie_id"

            fun createArgs(movieId: String): Bundle =
                bundleOf(ARGS_MOVIE_ID to movieId)
                }
            }

