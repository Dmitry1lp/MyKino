package com.practicum.mykino.activitys.data.network

import com.practicum.mykino.activitys.domain.models.MovieCast
import com.practicum.mykino.activitys.data.cast.FullCastRequest
import com.practicum.mykino.activitys.data.cast.FullCastResponse
import com.practicum.mykino.activitys.data.converter.MovieCastConverter
import com.practicum.mykino.activitys.data.movies.dto.MovieDetailsRequest
import com.practicum.mykino.activitys.data.movies.dto.MovieDetailsResponse
import com.practicum.mykino.activitys.data.movies.dto.MoviesSearchRequest
import com.practicum.mykino.activitys.data.movies.dto.MoviesSearchResponse
import com.practicum.mykino.activitys.domain.api.MoviesRepository
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.domain.models.MovieDetails
import com.practicum.mykino.activitys.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(private val networkClient: NetworkClient, private val movieCastConverter: MovieCastConverter,) : MoviesRepository {

    override fun searchMovies(expression: String): Flow<Resource<List<Movie>>> = flow {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                with(response as MoviesSearchResponse) {
                val data = response.results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)
                }
                emit(Resource.Success(data))
                }
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>> = flow {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))

        if (response.resultCode == 200) {
            val details = response as MovieDetailsResponse

             emit(Resource.Success(
                 MovieDetails(
                     id = details.id ?: "",
                     title = details.title ?: "",
                     imDbRating = details.imDbRating ?: "N/A",
                     year = details.year ?: "",
                     countries = details.countries ?: "",
                     genres = details.genres ?: "",
                     directors = details.directors ?: "",
                     writers = details.writers ?: "",
                     stars = details.stars ?: "",
                     plot = details.plot ?: ""
                 )))
        } else { emit(Resource.Error("Ошибка сервера")) }
    }

    override fun getFullCastData(movieId: String): Flow<Resource<MovieCast>> = flow {
        val response = networkClient.doRequest(FullCastRequest(movieId))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }
            200 -> {

                emit(Resource.Success(
                    data = movieCastConverter.convert(response as FullCastResponse)
                ))
            }
            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }
}
