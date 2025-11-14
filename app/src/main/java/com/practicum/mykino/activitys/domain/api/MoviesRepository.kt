package com.practicum.mykino.activitys.domain.api

import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.domain.models.MovieCast
import com.practicum.mykino.activitys.domain.models.MovieDetails
import com.practicum.mykino.activitys.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovies(expression: String): Flow<Resource<List<Movie>>>

    fun getMovieDetails(movieId: String) : Flow<Resource<MovieDetails>>

    fun getFullCastData(movieId: String): Flow<Resource<MovieCast>>
}