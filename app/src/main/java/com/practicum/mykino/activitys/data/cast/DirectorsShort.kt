package com.practicum.mykino.activitys.data.cast

data class DirectorsShort(
    val job: String,
    val items: List<CastShortItem> = emptyList()
)
