package com.practicum.mykino.activitys.presentation.cast

import com.practicum.mykino.activitys.core.ui.RVItem
import com.practicum.mykino.activitys.domain.models.MovieCastPerson

sealed interface MoviesCastRVItem: RVItem {

    data class HeaderItem(
        val headerText: String,
    ) : MoviesCastRVItem

    data class PersonItem(
        val data: MovieCastPerson,
    ) : MoviesCastRVItem

}