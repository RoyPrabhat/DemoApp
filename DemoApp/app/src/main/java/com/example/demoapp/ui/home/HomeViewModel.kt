package com.example.demoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoapp.data.repository.HomePageRepository
import com.example.demoapp.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homePageRepository: HomePageRepository,
                                        @IoDispatcher private val ioDispatcher: CoroutineDispatcher) : ViewModel() {

    private val _homePageUiState = MutableStateFlow<HomePageUiState>(HomePageUiState.Loading)
    val homePageUiState = _homePageUiState.asStateFlow()

    init {
        getTopRatedMovie()
    }

    fun getTopRatedMovie() {
        viewModelScope.launch(ioDispatcher) {
            _homePageUiState.value = HomePageUiState.Loading
            homePageRepository.getTopRatedMovie().catch {
                _homePageUiState.value = HomePageUiState.Error(it)
            }.collect {
                _homePageUiState.value = HomePageUiState.Success(it)
            }
        }
    }

}