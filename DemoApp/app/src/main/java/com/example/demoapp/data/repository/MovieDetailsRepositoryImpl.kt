package com.example.demoapp.data.repository

import com.example.demoapp.data.api.DataSource
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(private val dataSource: DataSource) : MovieDetailsRepository{

    override suspend fun getMovieDetails(id : Long) = dataSource.getMovieData(id)

}