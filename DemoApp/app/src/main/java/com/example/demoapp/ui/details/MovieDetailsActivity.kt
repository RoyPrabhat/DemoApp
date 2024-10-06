package com.example.demoapp.ui.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.demoapp.R
import com.example.demoapp.data.model.Movie
import com.example.demoapp.databinding.MovieDetailsActivityBinding
import com.example.demoapp.utils.Constants.MOVIE_ID
import com.example.demoapp.utils.StringUtil
import com.example.demoapp.utils.makeGone
import com.example.demoapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var glide: RequestManager
    @Inject
    internal lateinit var similarMovieAdapter: SimilarMovieAdapter
    private val movieDetailsViewModel by viewModels<MovieDetailsViewModel>()
    private lateinit var viewBinding : MovieDetailsActivityBinding

    companion object {
        fun start(origin : Activity, id: Long) {
            origin.startActivity(
                Intent(origin, MovieDetailsActivity::class.java).apply {
                    putExtra(MOVIE_ID, id)
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = MovieDetailsActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        init()
    }

    private fun init() {
        setUpToolbar()
        setUpMovieDetailsFlowCollector()
        setUpSimilarMovieCollector()
        setupEventListeners()
    }

    private fun setUpToolbar() {
        with(viewBinding.toolbar) {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener{ finish() }
        }
    }

    private fun setupEventListeners() {
        viewBinding.retryCta.setOnClickListener {
            movieDetailsViewModel.fetchMovieDetails()
        }
    }

    private fun setUpMovieDetailsFlowCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailsViewModel.movieDetailsState.collect { state ->
                    handleUiStateChange(state)
                }
            }
        }
    }

    private fun setUpSimilarMovieCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailsViewModel.similarMovieState.collect { state ->
                    handleSimilarMoviesStateChange(state)
                }
            }
        }
    }

    private fun handleSimilarMoviesStateChange(state: SimilarMovieUIState) {
        when(state) {
            is SimilarMovieUIState.Error -> hideSimilarMovieView()
            is SimilarMovieUIState.Success -> showSimilarMoviesView(state.movie.results)
        }
    }

    private fun showSimilarMoviesView(similarMovies: List<Movie>?) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        with(viewBinding.similarMovieList) {
            layoutManager = linearLayoutManager
            adapter = similarMovieAdapter
        }
        similarMovieAdapter.setOnClick { id ->
            similarMovies?.get(id)?.id?.let { movieId ->
                start(this@MovieDetailsActivity, movieId)
            }
        }
        similarMovieAdapter.submitList(similarMovies)
        viewBinding.similarMoviesGroup.makeVisible()
    }

    private fun hideSimilarMovieView() {
        viewBinding.similarMoviesGroup.makeGone()
    }


    private fun handleUiStateChange(state: MovieDetailsUiState) {
        when(state) {
            is MovieDetailsUiState.Loading -> showLoadingState()
            is MovieDetailsUiState.Success -> showSuccessState(state.movie)
            is MovieDetailsUiState.Error -> showErrorState()
        }
    }

    private fun showErrorState() {
        viewBinding.retryCta.makeVisible()
        viewBinding.scrollView.makeGone()
        viewBinding.loader.makeGone()
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccessState(movie: Movie) {
        viewBinding.loader.makeGone()
        viewBinding.retryCta.makeGone()
        with(viewBinding) {
            movie.backdropPath?.let{
                val cornerRadius = resources.getDimension(R.dimen.dimen_16dp)
                glide.load(it.let {
                     StringUtil.buildImageUrl(it) }).transform(RoundedCorners(cornerRadius.toInt()))
                    .placeholder(ContextCompat.getDrawable(this@MovieDetailsActivity, R.drawable.bg_placeholder))
                    .into(movieImg)
            }
            movieTitle.text = movie.title
            movieOverview.text = movie.overview
        }
        viewBinding.scrollView.makeVisible()
    }

    private fun showLoadingState() {
        viewBinding.loader.makeVisible()
        viewBinding.scrollView.makeGone()
    }
}