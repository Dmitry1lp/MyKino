package com.practicum.mykino.activitys.presentation.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mykino.activitys.domain.api.MoviesInteractor
import com.practicum.mykino.activitys.ui.movies.models.AboutState
import kotlinx.coroutines.launch

class AboutViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = stateLiveData

    init {
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            stateLiveData.value = AboutState.Loading

            moviesInteractor.getMovieDetails(movieId).collect { (data, error) ->
                when {
                    data != null -> stateLiveData.value = AboutState.Content(data)
                    error != null -> stateLiveData.value = AboutState.Error(error)
                    else -> stateLiveData.value = AboutState.Error("Unknown error")
                }
            }
        }
    }
}