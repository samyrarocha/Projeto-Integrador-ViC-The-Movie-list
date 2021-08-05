package com.example.projeto_integrador.features.feed.presentation

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Logger
import com.example.projeto_integrador.common.domain.model.NetworkUnavailableException
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.models.mappers.UiMovieMapper
import com.example.projeto_integrador.features.feed.domain.usecases.RequestNextPageOfMovies
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllMoviesFragmentViewModel (
    private val uiMovieMapper: UiMovieMapper,
    private val requestNextPageOfMovies: RequestNextPageOfMovies,
    private val  dispatchersProvider: DispatcherProvider,
    private val compositeDisposable: CompositeDisposable
        ): ViewModel() {

    private val _state = MutableLiveData<AllMoviesViewState>()
    val state: LiveData<AllMoviesViewState>  = _state
    var isLoadingMoreAnimals: Boolean = false
    var isLastPage = false

    private var page = 0

    init {
        _state.value = AllMoviesViewState(loading = true)
    }


    fun onEvent(event: AllMoviesEvent){
        when(event) {
            is AllMoviesEvent.RequestInitialAnimalList -> loadMovies()
            is AllMoviesEvent.RequestMoreAnimals -> loadNextMoviePage()
        }
    }

    private fun loadMovies() {
        if (state.value!!.movies.isEmpty()) {
            loadNextMoviePage()
        }
    }

    private fun loadNextMoviePage() {
        val errorMessage = "Failed to fetch movies"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage){
            onFailure(it)
        }

        viewModelScope.launch(exceptionHandler) {
            val discover = withContext(dispatchersProvider.io()) {
                Logger.d("Requesting more movies")

                requestNextPageOfMovies(++page)
            }

            isLoadingMoreAnimals = false
        }
    }

    private fun onFailure(failure: Throwable) {
        when(failure) {
            is NetworkErrorException,
            is NetworkUnavailableException -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}