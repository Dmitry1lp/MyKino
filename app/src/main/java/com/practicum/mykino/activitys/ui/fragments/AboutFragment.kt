package com.practicum.mykino.activitys.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.practicum.mykino.R
import com.practicum.mykino.activitys.domain.models.MovieDetails
import com.practicum.mykino.activitys.presentation.about.AboutViewModel
import com.practicum.mykino.activitys.ui.cast.MoviesCastFragment
import com.practicum.mykino.activitys.ui.movies.models.AboutState
import com.practicum.mykino.databinding.FragmentAboutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AboutFragment: Fragment() {

    private val aboutViewModel: AboutViewModel by viewModel {
        parametersOf(requireArguments().getString(MOVIE_ID))
    }

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showErrorMessage(message: String) {
        binding.apply {
            details.visibility = View.GONE
            errorMessage.visibility = View.VISIBLE
            errorMessage.text = message
        }
    }

    private fun showDetails(movieDetails: MovieDetails) {
        binding.apply {
            details.visibility = View.VISIBLE
            errorMessage.visibility = View.GONE
            title.text = movieDetails.title
            ratingValue.text = movieDetails.imDbRating
            yearValue.text = movieDetails.year
            countryValue.text = movieDetails.countries
            genreValue.text = movieDetails.genres
            directorValue.text = movieDetails.directors
            writerValue.text = movieDetails.writers
            castValue.text = movieDetails.stars
            plot.text = movieDetails.plot
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aboutViewModel.observeState().observe(viewLifecycleOwner) {
            when(it){
                is AboutState.Error -> showErrorMessage(it.message)
                is AboutState.Content -> showDetails(it.movie)
                is AboutState.Loading -> null
            }
        }

        binding.bShowCast.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_moviesCastFragment,
                    MoviesCastFragment.createArgs(requireArguments().getString(MOVIE_ID).orEmpty()
                )
            )
        }
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun createArgs(movieId: String): Bundle =
            bundleOf(MOVIE_ID to movieId)
        }
    }

