package com.example.demoapp.ui.details

import androidx.lifecycle.SavedStateHandle
import com.example.demoapp.data.model.Movie
import com.example.demoapp.domain.MovieDetailsUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieDetailsViewModelTest {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var useCase: MovieDetailsUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    companion object {
        const val MOVIE_ID_KEY ="movieId"
        const val MOVIE_ID_VALUE = 1L
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        useCase = mockk(relaxed = true)
        savedStateHandle = mockk(relaxed = true)
        every { savedStateHandle.get<Long>(MOVIE_ID_KEY) } returns MOVIE_ID_VALUE
        viewModel = MovieDetailsViewModel(useCase, testDispatcher, savedStateHandle)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun teardown(){
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getMovieDetails - api returns success`() = runTest {
        var movieDetailsResponse = Movie()
        val response = flow<Movie> { movieDetailsResponse }
        coEvery { useCase.getMovieDetails(MOVIE_ID_VALUE) }  returns response

        viewModel.fetchMovieDetails()

        run {
            viewModel.movieDetailsState.value.apply {
                MovieDetailsUiState.Loading
                MovieDetailsUiState.Success(movieDetailsResponse)
            }
        }
    }

    @Test
    fun `getMovieDetails - api returns error`() = runTest {
        var errorResponse = Throwable()
        val response = flow<Movie> { errorResponse }
        coEvery { useCase.getMovieDetails(MOVIE_ID_VALUE) }  returns response

        viewModel.fetchMovieDetails()

        run {
            viewModel.movieDetailsState.value.apply {
                MovieDetailsUiState.Loading
                MovieDetailsUiState.Error(errorResponse)
            }
        }
    }
}