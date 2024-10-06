package com.example.demoapp.data.repository

import com.example.demoapp.data.api.ApiDataSource
import javax.inject.Inject

class HomePageRepository @Inject constructor(private val apiDataSource: ApiDataSource) {

    suspend fun getTopRatedMovie() = apiDataSource.getTopRatedMovies()
}