package com.practicum.mykino.activitys.ui.movies.models

import com.practicum.mykino.activitys.core.ui.RVItem

sealed interface MoviesCastState {

    object Loading : MoviesCastState

    data class Error(
        val message: String,
    ) : MoviesCastState

    data class Content(
        val fullTitle: String,
        val items: List<RVItem>,
    ) : MoviesCastState

}