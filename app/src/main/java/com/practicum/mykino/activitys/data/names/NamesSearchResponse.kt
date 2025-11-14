package com.practicum.mykino.activitys.data.names

import com.practicum.mykino.activitys.data.movies.dto.Response

class NamesSearchResponse(val searchType: String,
                          val expression: String,
                          val results: List<PersonDto>) : Response()