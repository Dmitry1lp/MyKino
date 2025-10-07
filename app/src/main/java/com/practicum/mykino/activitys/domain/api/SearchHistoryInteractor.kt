package com.practicum.mykino.activitys.domain.api

import com.practicum.mykino.activitys.domain.models.Movie

interface SearchHistoryInteractor {

    fun getHistory(consumer: HistoryConsumer)
    fun saveToHistory(m: Movie)

    interface HistoryConsumer {
        fun consume(searchHistory: List<Movie>?)
    }
}