package com.example.demoapp.ui.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import com.example.demoapp.ui.theme.DemoApplicationTheme
import com.example.demoapp.utils.Constants.MOVIE_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        fun start(origin : Activity, id: Long) {
            origin.startActivity(
                Intent(origin, MovieDetailsActivity::class.java).apply {
                    putExtra(MOVIE_ID, id)
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoApplicationTheme {
                Surface(color = Color.White) {
                    DetailsScreenComposable(::goBack)
                }
            }
        }
    }

    private fun goBack() {
        finish()
    }
}