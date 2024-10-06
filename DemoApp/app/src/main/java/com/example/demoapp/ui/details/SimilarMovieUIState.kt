package com.example.demoapp.ui.details

import com.example.demoapp.data.model.MovieList

sealed class SimilarMovieUIState {
    data class Error(val e : Throwable) : SimilarMovieUIState()
    data class Success(val movie : MovieList) : SimilarMovieUIState()
}