package com.example.demoapp.data.repository

import com.example.demoapp.data.api.ApiDataSource
import javax.inject.Inject

class HomePageRepositoryImpl @Inject constructor(private val apiDataSource: ApiDataSource) : HomePageRepository {

    override suspend fun getTopRatedMovie() = apiDataSource.getTopRatedMovies()
}