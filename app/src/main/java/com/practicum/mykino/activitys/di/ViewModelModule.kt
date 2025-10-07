package com.practicum.mykino.activitys.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.practicum.mykino.activitys.presentation.movies.MoviesViewModel
import com.practicum.mykino.activitys.presentation.movies.PosterViewModel
import com.practicum.mykino.activitys.presentation.about.AboutViewModel
import org.koin.android.ext.koin.androidContext


val viewModelModule = module {

    viewModel {
        MoviesViewModel(androidContext(),get())
    }

    viewModel {(movieId: String) ->
        AboutViewModel(movieId, get())
    }

    viewModel {(posterUrl: String) ->
        PosterViewModel(posterUrl)
    }


}