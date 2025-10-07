package com.practicum.mykino.activitys.di

import android.content.Context
import com.google.gson.Gson
import com.practicum.mykino.activitys.data.network.IMDbApiService
import com.practicum.mykino.activitys.data.network.NetworkClient
import com.practicum.mykino.activitys.data.network.SearchHistoryStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.practicum.mykino.activitys.data.local.SharedPreferencesSearchHistoryStorage
import com.practicum.mykino.activitys.data.network.RetrofitNetworkClient


val dataModule = module {

    single<IMDbApiService> {
        Retrofit.Builder()
            .baseUrl("https://tv-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IMDbApiService::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    }

    factory { Gson() }

    single<SearchHistoryStorage> {
        SharedPreferencesSearchHistoryStorage(get(), get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }

}