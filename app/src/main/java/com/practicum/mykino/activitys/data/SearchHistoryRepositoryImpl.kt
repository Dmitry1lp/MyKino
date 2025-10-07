package com.practicum.mykino.activitys.data

import com.practicum.mykino.activitys.data.network.StorageClient
import com.practicum.mykino.activitys.domain.api.SearchHistoryRepository
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.util.Resource

class SearchHistoryRepositoryImpl(
    private val storage: StorageClient<ArrayList<Movie>>
): SearchHistoryRepository {

    override fun saveToHistory(m: Movie) {
        val movies = storage.getData() ?: arrayListOf()
        movies.add(m)
        storage.storeData(movies)
    }

    override fun getHistory(): Resource<List<Movie>> {
        val movies = storage.getData() ?: listOf()
        return Resource.Success(movies)
    }
}