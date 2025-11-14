package com.practicum.mykino.activitys.domain.api

import com.practicum.mykino.activitys.domain.models.Person
import com.practicum.mykino.activitys.util.Resource
import kotlinx.coroutines.flow.Flow

interface NamesRepository {

    fun searchNames(expression: String): Flow<Resource<List<Person>>>
}