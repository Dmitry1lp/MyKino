package com.practicum.mykino.activitys.di

import com.practicum.mykino.activitys.domain.api.MoviesInteractor
import com.practicum.mykino.activitys.domain.api.NamesInteractor
import com.practicum.mykino.activitys.domain.api.SearchHistoryInteractor
import org.koin.dsl.module
import com.practicum.mykino.activitys.domain.impl.MoviesInteractorImpl
import com.practicum.mykino.activitys.domain.impl.NamesInteractorImpl
import com.practicum.mykino.activitys.domain.impl.SearchHistoryInteractorImpl


val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }

    single<SearchHistoryInteractor> {
        SearchHistoryInteractorImpl(get())
    }

    single<NamesInteractor> {
        NamesInteractorImpl(get())
    }

}