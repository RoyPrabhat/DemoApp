package com.example.demoapp.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoapp.data.repository.MovieDetailsRepository
import com.example.demoapp.di.IoDispatcher
import com.example.demoapp.utils.Constants.MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieDetailsRepository: MovieDetailsRepository,
                                                @IoDispatcher private val ioDispatcher: CoroutineDispatcher, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val filmId = savedStateHandle.get<Long>(MOVIE_ID)
    private val _movieDetailsResponse = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val movieDetailsState : StateFlow<MovieDetailsUiState>
        get() = _movieDetailsResponse

    init {
        getMovieDetails()
    }

    fun fetchMovieDetails() {
        getMovieDetails()
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
}