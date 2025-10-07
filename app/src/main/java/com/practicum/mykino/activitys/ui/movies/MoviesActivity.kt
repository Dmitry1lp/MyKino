package com.practicum.mykino.activitys.ui.movies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.mykino.activitys.domain.models.Movie
import com.practicum.mykino.activitys.presentation.movies.MoviesViewModel
import com.practicum.mykino.activitys.ui.movies.models.MoviesState
import com.practicum.mykino.activitys.ui.poster.DetailsActivity
import com.practicum.mykino.databinding.ActivityMoviesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesActivity: AppCompatActivity() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val viewModel by viewModel<MoviesViewModel>()

    private lateinit var binding: ActivityMoviesBinding

    private var textWatcher: TextWatcher? = null

    private val adapter = MoviesAdapter {
        if (clickDebounce()) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("poster", it.image)
            startActivity(intent)
        }
    }

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.movies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.movies.adapter = adapter

        viewModel?.observeState()?.observe(this) {
            render(it)
        }

        viewModel?.observeShowToast()?.observe(this) {
            if (it != null) {
                showToast(it)
            }
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable?) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel?.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }
        }
        textWatcher?.let { binding.queryInput.addTextChangedListener(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        textWatcher?.let { binding.queryInput.removeTextChangedListener(it) }
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    fun showLoading() {
        binding.apply {
            movies.visibility = View.GONE
            placeholderMessage.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }

    }

    fun showContent(moviesList: List<Movie>) {
        binding.apply {
            movies.visibility = View.VISIBLE
            placeholderMessage.visibility = View.GONE
            progressBar.visibility = View.GONE
        }


        adapter.movies.clear()
        adapter.movies.addAll(moviesList)
        adapter.notifyDataSetChanged()
    }

    fun showError(errorMessage: String) {
        binding.apply {
            movies.visibility = View.GONE
            placeholderMessage.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            placeholderMessage.text = errorMessage
        }
    }

    fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Empty -> showEmpty(state.message)
        }
    }
}
