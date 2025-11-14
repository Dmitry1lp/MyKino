package com.practicum.mykino.activitys.presentation.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mykino.activitys.domain.api.MoviesInteractor
import com.practicum.mykino.activitys.domain.models.MovieCast
import com.practicum.mykino.activitys.presentation.cast.MoviesCastRVItem
import com.practicum.mykino.activitys.ui.movies.models.MoviesCastState
import kotlinx.coroutines.launch

class MoviesCastViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<MoviesCastState>()
    fun observeState(): LiveData<MoviesCastState> = stateLiveData

    init {
        loadFullCastData()
    }

    private fun loadFullCastData() {
        viewModelScope.launch {
            stateLiveData.value = MoviesCastState.Loading

            moviesInteractor.getFullCastData(movieId).collect { (data, error) ->
                when {
                    data != null -> {
                        val uiState = castToUiStateContent(data)
                        stateLiveData.value = uiState
                    }
                    error != null -> stateLiveData.value = MoviesCastState.Error(error)
                    else -> stateLiveData.value = MoviesCastState.Error("Unknown error")
                }

            }
        }
    }


    private fun castToUiStateContent(cast: MovieCast): MoviesCastState {

        val items = buildList<MoviesCastRVItem> {

            if (cast.directors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Directors")
                this += cast.directors.map { MoviesCastRVItem.PersonItem(it) }
            }

            if (cast.writers.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Writers")
                this += cast.writers.map { MoviesCastRVItem.PersonItem(it) }
            }

            if (cast.actors.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Actors")
                this += cast.actors.map { MoviesCastRVItem.PersonItem(it) }
            }

            if (cast.others.isNotEmpty()) {
                this += MoviesCastRVItem.HeaderItem("Others")
                this += cast.others.map { MoviesCastRVItem.PersonItem(it) }
            }
        }

        return MoviesCastState.Content(
            fullTitle = cast.fullTitle,
            items = items
        )
    }
}