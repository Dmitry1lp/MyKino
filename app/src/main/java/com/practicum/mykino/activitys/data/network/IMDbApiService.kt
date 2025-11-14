package com.practicum.mykino.activitys.data.network

import com.practicum.mykino.activitys.data.cast.FullCastResponse
import com.practicum.mykino.activitys.data.movies.dto.MovieDetailsResponse
import com.practicum.mykino.activitys.data.movies.dto.MoviesSearchResponse
import com.practicum.mykino.activitys.data.names.NamesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    suspend fun searchMovies(@Path("expression") expression: String): MoviesSearchResponse

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: String): MovieDetailsResponse

    @GET("/en/API/FullCast/k_zcuw1ytf/{movie_id}")
    suspend fun getFullCast(@Path("movie_id") movieId: String): FullCastResponse

    @GET("/en/API/SearchName/k_zcuw1ytf/{expression}")
    suspend fun searchName(@Path("expression") expression: String): NamesSearchResponse
}