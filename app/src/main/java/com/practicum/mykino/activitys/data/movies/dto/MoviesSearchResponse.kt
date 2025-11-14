package com.practicum.mykino.activitys.data.movies.dto

class MoviesSearchResponse(val searchType: String,
                           val expression: String,
                           val results: List<MovieDto>) : Response()