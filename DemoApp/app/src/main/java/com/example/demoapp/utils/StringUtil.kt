package com.example.demoapp.utils

object StringUtil {

    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    fun buildImageUrl(path : String) = "${IMAGE_BASE_URL}${path}"
}