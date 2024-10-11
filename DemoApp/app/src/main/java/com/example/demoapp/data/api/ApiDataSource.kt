package com.example.demoapp.data.api

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiDataSource @Inject constructor(private val movieService: MovieService) : DataSource {

    override suspend fun getTopRatedMovies() = flow {
        emit(movieService.getTopRatedMovies())
    }

    override suspend fun getMovieData(id: Long) = flow {
        emit(movieService.getMovieData(id))
    }
}