package com.example.demoapp.domain

import com.example.demoapp.data.repository.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(private val movieDetailsRepository: MovieDetailsRepository) {

    suspend fun getMovieDetails(id: Long) = movieDetailsRepository.getMovieDetails(id)
}