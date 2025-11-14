package com.practicum.mykino.activitys.data.cast

import com.practicum.mykino.activitys.data.movies.dto.Response

data class FullCastResponse(
    val actors: List<ActorShort>,
    val directors: DirectorsShort,
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val others: List<OtherShort>,
    val title: String,
    val type: String,
    val writers: WritersShort,
    val year: String
) : Response()