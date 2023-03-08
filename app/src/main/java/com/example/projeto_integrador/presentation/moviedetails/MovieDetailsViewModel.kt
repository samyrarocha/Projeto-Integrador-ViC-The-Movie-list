package com.example.projeto_integrador.presentation.moviedetails

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto_integrador.domain.mappers.UiMovieDetailsMapper
import com.example.projeto_integrador.domain.model.NetworkUnavailableException
import com.example.projeto_integrador.domain.model.NoMoreItemsException
import com.example.projeto_integrador.domain.model.movies.details.MovieDetails
import com.example.projeto_integrador.presentation.feed.models.Event
import com.example.projeto_integrador.domain.uttils.DispatchersProviderImp
import com.example.projeto_integrador.domain.usecases.GetMovieDetailsUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime

class MovieDetailsViewModel(
    private val uiMovieDetailsMapper: UiMovieDetailsMapper,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val dispatchersProvider: DispatchersProviderImp
): ViewModel() {


    private val _state = MutableLiveData<MovieDetailsViewState>()
    val state: LiveData<MovieDetailsViewState> = _state


    init {
        _state.value = MovieDetailsViewState(loading = true)
    }

    @ExperimentalTime
    fun getMovieDetails(movieId: Long) {

        viewModelScope.launch {
            runCatching {
                withContext(dispatchersProvider.io()) {
                   getMovieDetailsUseCase(movieId)
                }
            }.onSuccess {
                handleSuccess(it)
            }.onFailure {
                onFailure(it)
            }
        }
    }


    @ExperimentalTime
    private fun handleSuccess(movieDetails: MovieDetails) {
        _state.value = _state.value?.copy(
            loading = false,
            movieDetails = uiMovieDetailsMapper.mapToView(movieDetails)
        )
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkErrorException,
            is NetworkUnavailableException -> {
                _state.value = _state.value?.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
            is NoMoreItemsException -> {
                _state.value = _state.value?.copy(
                    loading = false,
                    noMoreMovies = true,
                    failure = Event(failure)
                )
            }
        }
    }
}