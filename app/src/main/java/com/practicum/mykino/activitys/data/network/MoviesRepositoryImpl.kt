package com.practicum.mykino.activitys.data.network

import com.practicum.mykino.activitys.data.dto.MovieDetailsRequest
import com.practicum.mykino.activitys.data.dto.MovieDetailsResponse
import com.practicum.mykino.activitys.data.dto.MoviesSearchRequest
import com.practicum.mykino.activitys.data.dto.MoviesSearchResponse
import com.practicum.mykino.activitys.domain.api.MoviesRepository
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.domain.models.MovieDetails
import com.practicum.mykino.activitys.util.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)
                })
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun getMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))

        return if (response.resultCode == 200) {
            val details = response as MovieDetailsResponse
             Resource.Success(
                 MovieDetails(
                id = details.id,
                title = details.title,
                imDbRating = details.imDbRating,
                year = details.year,
                countries = details.countries,
                genres = details.genres,
                directors = details.directors,
                writers = details.writers,
                stars = details.stars,
                plot = details.plot
            ))
        } else { Resource.Error("Ошибка сервера") }
    }
}
