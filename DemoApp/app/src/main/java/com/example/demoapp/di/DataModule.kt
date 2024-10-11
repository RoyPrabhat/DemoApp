package com.example.demoapp.di

import com.example.demoapp.data.api.DataSource
import com.example.demoapp.data.api.ApiDataSource
import com.example.demoapp.data.repository.HomePageRepository
import com.example.demoapp.data.repository.HomePageRepositoryImpl
import com.example.demoapp.data.repository.MovieDetailsRepository
import com.example.demoapp.data.repository.MovieDetailsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    abstract fun homePageRepository(impl: HomePageRepositoryImpl): HomePageRepository

    @Binds
    abstract fun movieDetailsRepository(impl: MovieDetailsRepositoryImpl): MovieDetailsRepository

    @Binds
    abstract fun apiDataSource(impl: ApiDataSource): DataSource

}