package com.practicum.mykino.activitys.di

import com.practicum.mykino.activitys.domain.api.MoviesRepository
import org.koin.dsl.module
import com.practicum.mykino.activitys.data.network.MoviesRepositoryImpl
import com.practicum.mykino.activitys.domain.api.SearchHistoryRepository
import com.practicum.mykino.activitys.data.SearchHistoryRepositoryImpl

val repositoryModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }
}