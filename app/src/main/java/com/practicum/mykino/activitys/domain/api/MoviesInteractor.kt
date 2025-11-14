package com.practicum.mykino.activitys.domain.api

import com.practicum.mykino.activitys.data.cast.FullCastResponse
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.domain.models.MovieCast
import com.practicum.mykino.activitys.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesInteractor {
    fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>>

    fun getMovieDetails(movieId: String): Flow<Pair<MovieDetails?, String?>>

    fun getFullCastData(movieId: String): Flow<Pair<MovieCast?, String?>>
}