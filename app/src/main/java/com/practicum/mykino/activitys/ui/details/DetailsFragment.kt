package com.practicum.mykino.activitys.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.mykino.R
import com.practicum.mykino.activitys.ui.DetailsViewPagerAdapter
import com.practicum.mykino.databinding.FragmentDetailsBinding

class DetailsFragment: Fragment(R.layout.fragment_details) {


    private lateinit var binding: FragmentDetailsBinding
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val poster = requireArguments().getString(POSTER_URL) ?: ""
        val movieId = requireArguments().getString(MOVIE_ID) ?: ""

        binding.viewPager.adapter = DetailsViewPagerAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle,
            posterUrl = poster,
            movieId = movieId,)

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.poster)
                1 -> tab.text = getString(R.string.details)
            }
        }
        tabMediator.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabMediator.detach()
    }

    companion object {
        private const val MOVIE_ID = "movie_id"
        private const val POSTER_URL = "poster_url"

        fun createArgs(movieId: String, posterUrl: String): Bundle =
            bundleOf(
                MOVIE_ID to movieId,
                POSTER_URL to posterUrl
            )
        }
    }

