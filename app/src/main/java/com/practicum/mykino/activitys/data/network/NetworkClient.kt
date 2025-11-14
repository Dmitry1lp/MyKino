package com.practicum.mykino.activitys.data.network

import com.practicum.mykino.activitys.data.movies.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response

}