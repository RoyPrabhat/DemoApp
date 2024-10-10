package com.example.demoapp.data.api

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiDataSourceImpl @Inject constructor(private val movieService: MovieService) : ApiDataSource {

    override suspend fun getTopRatedMovies() = flow {
        emit(movieService.getTopRatedMovies())
    }

    override suspend fun getMovieData(id : Long) = flow {
        emit(movieService.getMovieData(id))
    }
}