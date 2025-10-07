package com.practicum.mykino.activitys.data.dto

import com.practicum.mykino.activitys.domain.models.Movie

class MoviesSearchResponse(val searchType: String,
                           val expression: String,
                           val results: List<MovieDto>) : Response()