package com.example.demoapp.data.repository

import com.example.demoapp.data.api.ApiDataSource
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val apiDataSource: ApiDataSource) {

    suspend fun getMovieDetails(id : Long) = apiDataSource.getMovieData(id)
}