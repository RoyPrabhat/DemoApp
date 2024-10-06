package com.example.demoapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieList(val results : List<Movie>? = null)

@Keep
data class Movie(val id : Long? = null,
     val title : String? = null,
     val overview : String? = null,
     @SerializedName("poster_path") val posterPath : String? = null,
     @SerializedName("backdrop_path") val backdropPath : String? = null,
     @SerializedName("vote_average") val voteAverage : String? = null,
     @SerializedName("release_date") val releaseDate : String? = null)