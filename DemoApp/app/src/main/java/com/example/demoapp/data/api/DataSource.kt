package com.example.demoapp.data.api

import com.example.demoapp.data.model.Movie
import com.example.demoapp.data.model.MovieList
import kotlinx.coroutines.flow.Flow

interface DataSource {

    suspend fun getTopRatedMovies(): Flow<MovieList>

    suspend fun getMovieData(id: Long): Flow<Movie>

}