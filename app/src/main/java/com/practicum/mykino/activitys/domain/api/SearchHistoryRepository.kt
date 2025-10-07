package com.practicum.mykino.activitys.domain.api

import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.util.Resource

interface SearchHistoryRepository {
    fun saveToHistory(m: Movie)
    fun getHistory(): Resource<List<Movie>>
}