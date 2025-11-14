package com.practicum.mykino.activitys.domain.impl

import com.practicum.mykino.activitys.domain.api.SearchHistoryInteractor
import com.practicum.mykino.activitys.domain.api.SearchHistoryRepository
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchHistoryInteractorImpl(private val repository: SearchHistoryRepository) : SearchHistoryInteractor {

    override fun getHistory(): Flow<List<Movie?>> {
        return repository.getHistory().map { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.map { it } ?: emptyList()
                }
                is Resource.Error -> {
                    emptyList()
                }
                is Resource.Loading -> {
                    emptyList()
                }
            }
        }
    }

    override fun saveToHistory(m: Movie): Flow<Movie?> {
        return repository.saveToHistory(m).map { result ->
            when (result) {
                is Resource.Success -> {
                    result.data
                }
                is Resource.Error -> {
                    null
                }
                is Resource.Loading -> {
                    null
                }
            }
        }
    }
}