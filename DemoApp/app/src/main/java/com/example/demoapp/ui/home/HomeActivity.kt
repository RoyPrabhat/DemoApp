package com.example.demoapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import com.example.demoapp.data.model.Movie
import com.example.demoapp.ui.details.MovieDetailsActivity
import com.example.demoapp.ui.theme.DemoApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoApplicationTheme {
                Surface(color = Color.White) {
                    HomeScreenComposable(::setItemClockListener)
                }
            }
        }
    }

    private fun setItemClockListener(movieList: Movie) {
            movieList.id?.let {movieId ->
                MovieDetailsActivity.start(this@HomeActivity, movieId)
        }
    }
}