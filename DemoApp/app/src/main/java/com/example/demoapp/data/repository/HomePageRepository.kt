package com.example.demoapp.data.repository

import com.example.demoapp.data.model.MovieList
import kotlinx.coroutines.flow.Flow

interface HomePageRepository {
    suspend fun getTopRatedMovies(): Flow<MovieList>

}