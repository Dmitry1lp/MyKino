package com.practicum.mykino.activitys.domain.impl

import com.practicum.mykino.activitys.domain.api.MoviesInteractor
import com.practicum.mykino.activitys.domain.api.MoviesRepository
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.domain.models.MovieCast
import com.practicum.mykino.activitys.domain.models.MovieDetails
import com.practicum.mykino.activitys.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {


    override fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>> {
        return repository.searchMovies(expression).map { result ->
            when(result) {
                is Resource.Success -> { Pair(result.data, null) }
                is Resource.Error -> { Pair(null, result.message) }
                is Resource.Loading -> Pair(emptyList(), null)            }
        }
    }

    override fun getMovieDetails(movieId: String): Flow<Pair<MovieDetails?, String?>> {
        return repository.getMovieDetails(movieId).map { result ->
            when(result) {
                is Resource.Success -> { Pair(result.data, null) }
                is Resource.Error -> { Pair(null, result.message) }
                is Resource.Loading -> Pair(null, null)
            }
        }
    }

    override fun getFullCastData(movieId: String): Flow<Pair<MovieCast?, String?>> {
        return repository.getFullCastData(movieId).map { result ->
            when(result) {
                is Resource.Success -> {Pair(result.data, null)}
                is Resource.Error -> {Pair(null, result.message)}
                is Resource.Loading -> Pair(null, null)
            }
        }
    }
}