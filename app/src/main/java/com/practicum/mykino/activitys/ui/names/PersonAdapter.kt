package com.practicum.mykino.activitys.ui.names

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.mykino.activitys.domain.models.Person
import com.practicum.mykino.databinding.ListItemPersonBinding

class PersonAdapter: RecyclerView.Adapter<PersonViewHolder>() {

    val persons = ArrayList<Person>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):PersonViewHolder {
        val binding = ListItemPersonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PersonViewHolder,
        position: Int
    ) {
        holder.bind(persons[position])
    }

    override fun getItemCount(): Int = persons.size
}