package com.practicum.mykino.activitys.domain.api

import com.practicum.mykino.activitys.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface SearchHistoryInteractor {

    fun getHistory(): Flow<List<Movie?>>
    fun saveToHistory(m: Movie): Flow<Movie?>

}