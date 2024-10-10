package com.example.demoapp.data.repository

import com.example.demoapp.data.api.ApiDataSource
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(private val apiDataSource: ApiDataSource) : MovieDetailsRepository{

    override suspend fun getMovieDetails(id : Long) = apiDataSource.getMovieData(id)
}