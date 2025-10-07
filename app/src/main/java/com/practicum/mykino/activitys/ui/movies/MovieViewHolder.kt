package com.practicum.mykino.activitys.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.mykino.R
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.databinding.ListItemMovieBinding

class MovieViewHolder(private val binding: ListItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        Glide.with(itemView)
            .load(movie.image)
            .into(binding.cover)

        binding.title.text = movie.title
        binding.description.text = movie.description
    }

    companion object {
        fun from(parent: ViewGroup): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemMovieBinding.inflate(inflater, parent, false)
            return MovieViewHolder(binding)
        }
    }
}