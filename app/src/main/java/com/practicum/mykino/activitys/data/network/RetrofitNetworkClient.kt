package com.practicum.mykino.activitys.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.practicum.mykino.activitys.data.cast.FullCastRequest
import com.practicum.mykino.activitys.data.movies.dto.MovieDetailsRequest
import com.practicum.mykino.activitys.data.movies.dto.MoviesSearchRequest
import com.practicum.mykino.activitys.data.movies.dto.Response
import com.practicum.mykino.activitys.data.names.NamesSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(private val iMDbApiService:IMDbApiService,private val context: Context
    ) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (isConnected() == false) {
            return Response().apply { resultCode = -1 }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response: Response = when(dto){
                    is NamesSearchRequest -> iMDbApiService.searchName(dto.expression)
                    is MoviesSearchRequest -> iMDbApiService.searchMovies(dto.expression)
                    is MovieDetailsRequest -> iMDbApiService.getMovieDetails(dto.movieId)
                    is FullCastRequest -> iMDbApiService.getFullCast(dto.movieId)
                    else -> return@withContext Response().apply { resultCode = 400 }
                }
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}