package com.practicum.mykino.activitys.di

import com.practicum.mykino.activitys.domain.api.MoviesRepository
import org.koin.dsl.module
import com.practicum.mykino.activitys.data.network.MoviesRepositoryImpl
import com.practicum.mykino.activitys.domain.api.SearchHistoryRepository
import com.practicum.mykino.activitys.data.SearchHistoryRepositoryImpl
import com.practicum.mykino.activitys.data.converter.MovieCastConverter
import com.practicum.mykino.activitys.data.network.NamesRepositoryImpl
import com.practicum.mykino.activitys.domain.api.NamesRepository

val repositoryModule = module {

    factory { MovieCastConverter() }

    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }

    single<NamesRepository> {
        NamesRepositoryImpl(get())
    }
}