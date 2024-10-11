package com.example.demoapp.utils

import com.example.demoapp.data.model.Genre

object StringUtil {

    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    fun buildImageUrl(path: String) = "${IMAGE_BASE_URL}${path}"

    fun getGenresList(genres: List<Genre>): String {
        val lastIndex = genres.lastIndex
        var concatGenreList = ""
        for ((index, genre) in genres.withIndex()) {
            concatGenreList += if (lastIndex == index) {
                "${genre.name}"
            } else {
                "${genre.name} - "
            }
        }
        return concatGenreList
    }
}