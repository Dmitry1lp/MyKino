package com.practicum.mykino.activitys.domain.api

import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.domain.models.MovieDetails
import com.practicum.mykino.activitys.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>

    fun getMovieDetails(movieId: String) : Resource<MovieDetails>
}