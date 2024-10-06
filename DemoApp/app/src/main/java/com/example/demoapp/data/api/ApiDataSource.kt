package com.example.demoapp.data.api

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiDataSource @Inject constructor(private val movieService: MovieService) {

    suspend fun getTopRatedMovies() = flow {
        emit(movieService.getTopRatedMovies())
    }

    suspend fun getMovieData(id : Long) = flow {
        emit(movieService.getMovieData(id))
    }

    suspend fun getSimilarMovies(id : Long) = flow {
        emit(movieService.getSimilarMovies(id))
    }
}