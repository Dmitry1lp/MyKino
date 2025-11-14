package com.practicum.mykino.activitys.domain.api

import com.practicum.mykino.activitys.domain.models.Person
import kotlinx.coroutines.flow.Flow

interface NamesInteractor {

    fun searchNames(expression: String): Flow<Pair<List<Person>?, String?>>
}