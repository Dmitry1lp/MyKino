package com.practicum.mykino.activitys.data

import com.practicum.mykino.activitys.data.network.StorageClient
import com.practicum.mykino.activitys.domain.api.SearchHistoryRepository
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchHistoryRepositoryImpl(
    private val storage: StorageClient<ArrayList<Movie>>
): SearchHistoryRepository {

    override fun saveToHistory(m: Movie): Flow<Resource<Movie>> = flow {
        val movies = storage.getData() ?: arrayListOf()
        movies.add(m)
        storage.storeData(movies)

        emit(Resource.Success(m))
    }

    override fun getHistory(): Flow<Resource<List<Movie>>> = flow {
        val movies = storage.getData() ?: listOf()
        emit(Resource.Success(movies))
    }
}