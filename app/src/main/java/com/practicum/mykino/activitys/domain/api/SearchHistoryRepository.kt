package com.practicum.mykino.activitys.domain.api

import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun saveToHistory(m: Movie): Flow<Resource<Movie>>
    fun getHistory(): Flow<Resource<List<Movie>>>
}
