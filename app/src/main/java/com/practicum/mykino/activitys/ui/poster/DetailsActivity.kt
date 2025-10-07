package com.practicum.mykino.activitys.ui.poster

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.mykino.R
import com.practicum.mykino.activitys.presentation.movies.PosterViewModel
import com.practicum.mykino.activitys.ui.DetailsViewPagerAdapter
import com.practicum.mykino.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val poster = intent.getStringExtra("poster") ?: ""
        val movieId = intent.getStringExtra("id") ?: ""

        binding.viewPager.adapter = DetailsViewPagerAdapter(
            fragmentManager = supportFragmentManager,
            lifecycle = lifecycle,
            posterUrl = poster,
            movieId = movieId,
        )

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.poster)
                1 -> tab.text = getString(R.string.details)
            }
        }
        tabMediator.attach()
    }


}