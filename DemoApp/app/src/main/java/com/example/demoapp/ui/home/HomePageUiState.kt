package com.example.demoapp.ui.home

import com.example.demoapp.data.model.MovieList

sealed class HomePageUiState {

    data object Loading : HomePageUiState()

    data class Error(val e : Throwable) : HomePageUiState()

    data class Success(val movieList : MovieList) : HomePageUiState()

}