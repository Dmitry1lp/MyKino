package com.practicum.mykino.activitys.data.network

interface StorageClient<T> {
    fun storeData(data: T)
    fun getData(): T?
}