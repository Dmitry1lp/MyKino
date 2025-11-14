package com.practicum.mykino.activitys.domain.impl

import com.practicum.mykino.activitys.domain.models.Person
import com.practicum.mykino.activitys.domain.api.NamesInteractor
import com.practicum.mykino.activitys.domain.api.NamesRepository
import com.practicum.mykino.activitys.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executors

class NamesInteractorImpl(private val repository: NamesRepository): NamesInteractor {

    override fun searchNames(expression: String): Flow<Pair<List<Person>?, String?>> {
        return repository.searchNames(expression).map { result ->
            when(result) {
                is Resource.Success -> {Pair(result.data, null)}
                is Resource.Error -> {Pair(null, result.message)}
                is Resource.Loading -> Pair(null, null)
            }
        }
    }
}