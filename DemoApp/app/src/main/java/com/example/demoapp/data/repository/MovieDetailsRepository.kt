package com.example.demoapp.data.repository

import com.example.demoapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    suspend fun getMovieDetails(id : Long) : Flow<Movie>
}