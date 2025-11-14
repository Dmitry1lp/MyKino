package com.practicum.mykino.activitys.ui.movies

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.mykino.R
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.presentation.movies.MoviesViewModel
import com.practicum.mykino.activitys.ui.details.DetailsFragment
import com.practicum.mykino.activitys.ui.main.RootActivity
import com.practicum.mykino.activitys.ui.movies.models.MoviesState
import com.practicum.mykino.activitys.util.debounce
import com.practicum.mykino.databinding.FragmentMoviesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment: Fragment(R.layout.fragment_movies) {

        companion object {
            private const val CLICK_DEBOUNCE_DELAY = 300L
        }

        private val viewModel by viewModel<MoviesViewModel>()

        private lateinit var binding: FragmentMoviesBinding
        private lateinit var onMovieClickDebounce: (Movie) -> Unit
        private var adapter: MoviesAdapter? = null

        private var textWatcher: TextWatcher? = null

    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onMovieClickDebounce = debounce<Movie>(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { movie ->
            findNavController().navigate(R.id.action_moviesFragment_to_detailsFragment,
                DetailsFragment.createArgs(movie.id, movie.image))
        }

        adapter = MoviesAdapter { movie ->
            (activity as RootActivity).animateBottomNavigationView()
            onMovieClickDebounce(movie)
        }

        binding.movies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.movies.adapter = adapter

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.observeShowToast().observe(viewLifecycleOwner) {
            if (it != null) {
                showToast(it)
            }
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }
        }
        textWatcher?.let { binding.queryInput.addTextChangedListener(it) }
    }

        override fun onDestroyView() {
            super.onDestroyView()
            adapter = null
            binding.movies.adapter = null
            textWatcher?.let { binding.queryInput.removeTextChangedListener(it) }
        }

        fun showLoading() {
            binding.apply {
                movies.visibility = View.GONE
                placeholderMessage.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        }

        fun showContent(moviesList: List<Movie>) {
            with(binding) {
                movies.visibility = View.VISIBLE
                placeholderMessage.visibility = View.GONE
                progressBar.visibility = View.GONE
            }

            adapter?.movies?.clear()
            adapter?.movies?.addAll(moviesList)
            adapter?.notifyDataSetChanged()
        }

        fun showError(errorMessage: String) {
            binding.apply {
                movies.visibility = View.GONE
                placeholderMessage.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                placeholderMessage.text = errorMessage
            }
        }

        fun showEmpty(emptyMessage: String) {
            showError(emptyMessage)
        }

        fun showToast(message: String) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }

        fun render(state: MoviesState) {
            when (state) {
                is MoviesState.Loading -> showLoading()
                is MoviesState.Content -> showContent(state.movies)
                is MoviesState.Error -> showError(state.errorMessage)
                is MoviesState.Empty -> showEmpty(state.message)
            }
        }
    }