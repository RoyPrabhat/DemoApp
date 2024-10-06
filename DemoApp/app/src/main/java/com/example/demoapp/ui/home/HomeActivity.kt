package com.example.demoapp.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoapp.R
import com.example.demoapp.data.model.Movie
import com.example.demoapp.databinding.MovieListActivityBinding
import com.example.demoapp.ui.details.MovieDetailsActivity
import com.example.demoapp.utils.makeGone
import com.example.demoapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    internal lateinit var movieAdapter : MovieAdapter
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var viewBinding : MovieListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = MovieListActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        init()
    }

    private fun init() {
        setUpUIStateFlowCollector()
        setUpRecyclerView()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        viewBinding.retryCta.setOnClickListener {
            homeViewModel.getTopRatedMovie()
        }
    }

    private fun setUpRecyclerView() {
        with(viewBinding.movieList) {
            val linearLayoutManager = LinearLayoutManager(this@HomeActivity)
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = movieAdapter
            val drawable = ResourcesCompat.getDrawable(this@HomeActivity.resources, R.drawable.divider, null)
            drawable?.let {
                val itemDecorator = DividerItemDecoration(this@HomeActivity, linearLayoutManager.orientation).apply { it }
                addItemDecoration(itemDecorator)
            }
        }
    }

    private fun setUpUIStateFlowCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homePageUiState.collect { state ->
                    handleUiStateChange(state)
                }
            }
        }
    }

    private fun handleUiStateChange(state: HomePageUiState) {
        when(state) {
            is HomePageUiState.Loading -> showLoadingState()
            is HomePageUiState.Success -> showSuccessState(state.movieList.results)
            is HomePageUiState.Error -> showErrorState()
        }
    }

    private fun showLoadingState() {
        viewBinding.loader.makeVisible()
    }

    private fun showErrorState() {
        viewBinding.retryCta.makeVisible()
        viewBinding.loader.makeGone()
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccessState(movieList: List<Movie>?) {
        setItemClockListener(movieList)
        movieAdapter.submitList(movieList)
        viewBinding.loader.makeGone()
        viewBinding.retryCta.makeGone()
    }

    private fun setItemClockListener(movieList: List<Movie>?) {
        movieAdapter.setOnClick { position ->
            movieList?.get(position)?.id?.let {movieId ->
                MovieDetailsActivity.start(this@HomeActivity, movieId)
            }
        }
    }
}