package com.practicum.mykino.activitys.presentation.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.mykino.activitys.domain.api.MoviesInteractor
import com.practicum.mykino.activitys.domain.models.MovieDetails
import com.practicum.mykino.activitys.ui.movies.models.AboutState

class AboutViewModel(private val movieId: String,
                     private val moviesInteractor: MoviesInteractor, ) : ViewModel() {

    private val stateLiveData = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = stateLiveData

    init {
        moviesInteractor.getMovieDetails(movieId, object : MoviesInteractor.MovieDetailsConsumer {

            override fun consume(movieDetails: MovieDetails?, errorMessage: String?) {
                if (movieDetails != null) {
                    stateLiveData.postValue(AboutState.Content(movieDetails))
                } else {
                    stateLiveData.postValue(AboutState.Error(errorMessage ?: "Unknown error"))
                }
            }
        })
    }
}