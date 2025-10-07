package com.practicum.mykino.activitys.data.network

import com.practicum.mykino.activitys.data.dto.MovieDetailsResponse
import com.practicum.mykino.activitys.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/YOUR_API_KEY/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

    @GET("/en/API/Title/YOUR_API_KEY/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse>
}