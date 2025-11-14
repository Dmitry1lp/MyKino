package com.practicum.mykino.activitys.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.mykino.R
import com.practicum.mykino.activitys.domain.api.MoviesInteractor
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.presentation.SingleLiveEvent
import com.practicum.mykino.activitys.presentation.names.NamesViewModel
import com.practicum.mykino.activitys.ui.movies.models.MoviesState
import com.practicum.mykino.activitys.util.debounce
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val context: Context,
    private val moviesInteractor: MoviesInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<MoviesState>()
    fun observeState(): LiveData<MoviesState> = stateLiveData
    private val showToast = SingleLiveEvent<String?>()
    fun observeShowToast(): LiveData<String?> = showToast
    private var latestSearchText: String? = null
    private val handler = Handler(Looper.getMainLooper())
    private val movieSearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true ) { changedText ->
        searchRequest(changedText)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            movieSearchDebounce(changedText)
        }
    }

    fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(
                MoviesState.Loading
            )
            viewModelScope.launch {
                moviesInteractor.searchMovies(newSearchText).collect { pair ->
                processResult(pair.first,pair.second)
                }
            }
        }
    }

    private fun processResult(foundMovies: List<Movie>?, errorMessage: String?) {
        val movies = mutableListOf<Movie>()
        if (foundMovies != null) {
            movies.addAll(foundMovies)
        }

        when {
            errorMessage != null -> {
                renderState(
                    MoviesState.Error(
                        errorMessage = context.getString(R.string.something_went_wrong))
                )
                showToast.postValue(errorMessage)
            }

            movies.isEmpty() -> {
                renderState(
                    MoviesState.Empty(
                        message = context.getString(R.string.nothing_found)
                        )
                )
            }

            else -> {
                renderState(
                    MoviesState.Content(
                        movies = movies
                    )
                )
            }
        }
    }

    private fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
    }
}