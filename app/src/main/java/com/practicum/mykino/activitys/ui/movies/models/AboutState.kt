package com.practicum.mykino.activitys.ui.movies.models

import com.practicum.mykino.activitys.domain.models.MovieDetails

sealed interface AboutState {

    object Loading : AboutState

    data class Content(
        val movie: MovieDetails
    ) : AboutState

    data class Error(
        val message: String
    ) : AboutState

}