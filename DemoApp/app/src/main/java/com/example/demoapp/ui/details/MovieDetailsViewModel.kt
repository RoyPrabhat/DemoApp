package com.example.demoapp.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoapp.data.model.MovieList
import com.example.demoapp.data.repository.MovieDetailsRepository
import com.example.demoapp.di.IoDispatcher
import com.example.demoapp.utils.Constants.MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieDetailsRepository: MovieDetailsRepository,
                                                @IoDispatcher private val ioDispatcher: CoroutineDispatcher, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val filmId = savedStateHandle.get<Long>(MOVIE_ID)
    private val _movieDetailsResponse = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val movieDetailsState = _movieDetailsResponse.asStateFlow()
    private val _similarMovieResponse = MutableStateFlow<SimilarMovieUIState>(SimilarMovieUIState.Success(MovieList(emptyList())))
    val similarMovieState = _similarMovieResponse.asStateFlow()

    init {
        fetchMovieDetails()
    }

    fun fetchMovieDetails() {
        getMovieDetails()
        getSimilarMovies()
    }

    private fun getMovieDetails() {
        viewModelScope.launch(ioDispatcher) {
            _movieDetailsResponse.value = MovieDetailsUiState.Loading
            filmId?.let {
                movieDetailsRepository.getMovieDetails(it).catch {
                    _movieDetailsResponse.value = MovieDetailsUiState.Error(it)
                }.collect {
                    _movieDetailsResponse.value = MovieDetailsUiState.Success(it)
                }
            }
        }
    }

    private fun getSimilarMovies() {
        viewModelScope.launch(ioDispatcher) {
            filmId?.let {
                movieDetailsRepository.getSimilarMovies(it).catch {
                    _similarMovieResponse.value = SimilarMovieUIState.Error(it)
                }.collect() {
                    _similarMovieResponse.value = SimilarMovieUIState.Success(it)
                }
            }
        }
    }
}