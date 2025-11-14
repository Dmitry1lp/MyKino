package com.practicum.mykino.activitys.ui.names

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.mykino.R
import com.practicum.mykino.activitys.domain.models.Person
import com.practicum.mykino.databinding.ListItemPersonBinding

class PersonViewHolder(private val binding: ListItemPersonBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(person: Person) {

        Glide.with(itemView)
            .load(person.photoUrl)
            .placeholder(R.drawable.ic_info)
            .circleCrop()
            .into(binding.photo)

        binding.name.text = person.name
        binding.description.text = person.description
    }
}