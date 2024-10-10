package com.example.demoapp.domain

import com.example.demoapp.data.repository.HomePageRepository
import javax.inject.Inject

class TopRatedMovieUseCase @Inject constructor(private val repository: HomePageRepository) {

    suspend fun getTopRatedMovies() = repository.getTopRatedMovies()

}