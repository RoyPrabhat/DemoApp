package com.example.demoapp.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.demoapp.data.model.Movie
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.demoapp.R
import com.example.demoapp.ui.common.ShowLoader
import com.example.demoapp.ui.theme.lightGray
import com.example.demoapp.utils.StringUtil

@Composable
fun HomeScreenComposable(onItemClick: (Movie) -> Unit) {
    val viewModel : HomeViewModel = viewModel()
    val state by viewModel.homePageUiState.collectAsState(initial = HomePageUiState.Loading)
    HomeScreen(state, onItemClick)
}

@Composable
fun HomeScreen(state: HomePageUiState, onItemClick: (Movie) -> Unit) {
    when(state) {
        is HomePageUiState.Loading -> ShowLoader()
        is HomePageUiState.Error -> {
            ShowErrorScreen()
        }
        is HomePageUiState.Success -> state.movieList.results?.let {
            ShowTopRatedMovies(it, onItemClick)
        }
    }
}
@Composable
fun ShowErrorScreen() {
    val context = LocalContext.current
    val viewModel : HomeViewModel = viewModel()
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Button(onClick = { viewModel.getTopRatedMovie() },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = ContextCompat.getString(context, R.string.retry_cta))
        }
        Toast.makeText(context, ContextCompat.getString(context, R.string.error_message), Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowTopRatedMovies(movieList: List<Movie>, onItemClick: (Movie) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "TOP RATED MOVIES",
            modifier = Modifier
                .padding(16.dp)
                .height(24.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Start)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(movieList, key = {item -> item.id!!}) { item ->
                MovieColumn(item, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun MovieColumn(item: Movie, onItemClick : (Movie) -> Unit) {
    Row(modifier = Modifier.clickable {onItemClick(item)}) {
        Column(modifier = Modifier.padding(16.dp,0.dp, 16.dp, 0.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(StringUtil.buildImageUrl(item.posterPath!!)).crossfade(true).build(),
                contentDescription = "",
                modifier = Modifier
                    .height(124.dp)
                    .width(92.dp),
            )
            Row {
                Text(text = item.releaseDate!!,
                    modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp),
                    fontWeight = FontWeight.Bold)
            }
        }
        Column(modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)) {
            Text(text = item.title!!, fontWeight = FontWeight.Bold, fontSize = 20.sp, maxLines = 1,  overflow = TextOverflow.Ellipsis,)
            Text(text = item.overview!!, maxLines = 5, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp))
        }
    }
    HorizontalDivider(thickness = 2.dp, modifier = Modifier
        .padding(16.dp)
        .background(color = lightGray))
}