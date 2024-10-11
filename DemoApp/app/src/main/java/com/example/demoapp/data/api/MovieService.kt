package com.example.demoapp.data.api

import com.example.demoapp.data.model.Movie
import com.example.demoapp.data.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): MovieList

    @GET("movie/{movie_id}")
    suspend fun getMovieData(@Path("movie_id") id: Long): Movie
}