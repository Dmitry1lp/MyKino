package com.practicum.mykino.activitys.domain.impl

import com.practicum.mykino.activitys.domain.api.SearchHistoryInteractor
import com.practicum.mykino.activitys.domain.api.SearchHistoryRepository
import com.practicum.mykino.activitys.domain.models.Movie

class SearchHistoryInteractorImpl(
    private val repository: SearchHistoryRepository
) : SearchHistoryInteractor {

    override fun getHistory(consumer: SearchHistoryInteractor.HistoryConsumer) {
        consumer.consume(repository.getHistory().data)
    }

    override fun saveToHistory(m: Movie) {
        repository.saveToHistory(m)
    }
}