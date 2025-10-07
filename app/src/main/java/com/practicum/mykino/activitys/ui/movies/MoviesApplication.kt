package com.practicum.mykino.activitys.ui.movies

import android.app.Application
import com.practicum.mykino.activitys.di.dataModule
import com.practicum.mykino.activitys.di.interactorModule
import com.practicum.mykino.activitys.di.repositoryModule
import com.practicum.mykino.activitys.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }
}