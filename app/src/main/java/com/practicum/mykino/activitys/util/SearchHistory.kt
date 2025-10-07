package com.practicum.mykino.activitys.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.mykino.activitys.domain.models.History
import com.practicum.mykino.activitys.domain.models.Movie

class SearchHistory(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("MOVIES_SEARCH", Context.MODE_PRIVATE)
    private val gson = Gson()

    private fun saveHistory(history: History) {
        prefs.edit().putString("HISTORY", gson.toJson(history, History::class.java)).apply()
    }

    fun getHistory(): History {
        val h = prefs.getString("HISTORY", null)
        if (h == null) {
            return History()
        } else {
            return gson.fromJson(h, History::class.java)
        }
    }

    fun addMovieToHistory(m: Movie) {
        val h = getHistory()
        h.movies.add(m)
        saveHistory(h)
    }
}