package com.practicum.mykino.activitys.data.network

import com.practicum.mykino.activitys.data.names.NamesSearchRequest
import com.practicum.mykino.activitys.data.names.NamesSearchResponse
import com.practicum.mykino.activitys.domain.api.NamesRepository
import com.practicum.mykino.activitys.domain.models.Person
import com.practicum.mykino.activitys.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NamesRepositoryImpl(private val networkClient: NetworkClient): NamesRepository {
    override fun searchNames(expression: String): Flow<Resource<List<Person>>> = flow {
        val response = networkClient.doRequest(NamesSearchRequest(expression))
        when(response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                with(response as NamesSearchResponse) {
                    val data = response.results.map {
                        Person(
                            id = it.id,
                            name = it.title,
                            description = it.description,
                            photoUrl = it.image)
                    }
                    emit(Resource.Success(data))
            }
        }
            else -> { emit(Resource.Error("Ошибка сервера")) }
    }
}
    }