package com.example.demoapp.ui.details

import com.example.demoapp.data.model.Movie

sealed class MovieDetailsUiState {
    data object Loading : MovieDetailsUiState()
    data class Error(val e : Throwable) : MovieDetailsUiState()
    data class Success(val movie : Movie) : MovieDetailsUiState()
}