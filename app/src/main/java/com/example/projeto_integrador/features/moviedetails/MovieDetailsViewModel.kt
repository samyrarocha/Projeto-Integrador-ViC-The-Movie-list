package com.example.projeto_integrador.features.moviedetails

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto_integrador.common.data.api.models.ApiMovieDetails
import com.example.projeto_integrador.common.domain.model.NetworkUnavailableException
import com.example.projeto_integrador.common.domain.model.NoMoreMoviesException
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.uttils.DispatchersProviderImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository,
    private val dispatchersProvider: DispatchersProviderImp,
): ViewModel() {


    private val _state = MutableLiveData<MovieDetailsViewState>()
    val state: LiveData<MovieDetailsViewState> = _state


    init {
        _state.value = MovieDetailsViewState(loading = true)
    }

    fun getMovieDetails(movieId: Long) {

        viewModelScope.launch {
            runCatching {
                withContext(dispatchersProvider.io()) {
                    moviesRepository.getMovieDetails(movieId)
                }
            }.onSuccess {
                handleSuccess(it)
            }.onFailure {
                onFailure(it)
            }
        }
    }

    private fun handleSuccess(movieDetails: ApiMovieDetails) {
        _state.value = _state.value?.copy(
            loading = false,
            movieDetails = movieDetails,
            genre = movieDetails.detailsGenreId
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
            is NoMoreMoviesException -> {
                _state.value = _state.value?.copy(
                    loading = false,
                    noMoreMovies = true,
                    failure = Event(failure)
                )
            }
        }
    }
}