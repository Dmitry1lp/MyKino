package com.practicum.mykino.activitys.data.network

import com.practicum.mykino.activitys.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}