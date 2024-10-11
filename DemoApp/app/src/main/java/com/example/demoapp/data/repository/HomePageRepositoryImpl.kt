package com.example.demoapp.data.repository

import com.example.demoapp.data.api.DataSource
import javax.inject.Inject

class HomePageRepositoryImpl @Inject constructor(private val dataSource: DataSource) : HomePageRepository {

    override suspend fun getTopRatedMovies() = dataSource.getTopRatedMovies()

}