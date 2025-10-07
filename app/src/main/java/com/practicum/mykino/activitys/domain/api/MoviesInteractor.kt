package com.practicum.mykino.activitys.domain.api

import com.practicum.mykino.activitys.data.dto.MovieDetailsResponse
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.domain.models.MovieDetails

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    fun getMovieDetails(movieId: String, consumer: MovieDetailsConsumer)

    interface MovieDetailsConsumer {
        fun consume(foundDetails: MovieDetails?, errorMessage: String?)
    }
}