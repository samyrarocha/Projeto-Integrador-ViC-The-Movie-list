package com.example.projeto_integrador.features.feed.presentation

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto_integrador.common.domain.model.NetworkUnavailableException
import com.example.projeto_integrador.common.domain.model.NoMoreMoviesException
import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.common.domain.model.movies.Movie
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.models.mappers.UiMovieMapper
import com.example.projeto_integrador.features.feed.di.GetMovies
import com.example.projeto_integrador.features.feed.domain.usecases.RequestNextPageOfMovies
import com.example.projeto_integrador.features.feed.uttils.DispatchersProvider
import com.example.projeto_integrador.features.feed.uttils.createExceptionHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllMoviesFragmentViewModel
    (
    private val uiMovieMapper: UiMovieMapper,
    private val requestNextPageOfMovies: RequestNextPageOfMovies,
    private val getMovies: GetMovies,
    private val  dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
        ): ViewModel() {

    private val _state = MutableLiveData<AllMoviesViewState>()
    val state: LiveData<AllMoviesViewState>  = _state
    var isLoadingMoreMovies: Boolean = false
    var isLastPage = false

    private var page = 0

    init {
        _state.value = AllMoviesViewState(loading = true)
        subscribeToMovieUpdate()
    }


    fun onEvent(event: AllMoviesEvent){
        when(event) {
            is AllMoviesEvent.RequestInitialMoviesList -> loadMovies()
            is AllMoviesEvent.RequestMoreMovies -> loadNextMoviePage()
        }
    }

    private fun subscribeToMovieUpdate() {
        getMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {onNewMovieList(it)},
                {onFailure(it)}
            )
    }

    private fun onNewMovieList(movies: List<Movie>) {
        val allMovies = movies.map { uiMovieMapper.mapToView(it) }

        val currentList = state.value!!.movies
        val newMovie = allMovies.subtract(currentList)
        val updateList = currentList + newMovie

        _state.value = state.value!!.copy(
            loading = false,
            movies = updateList
        )
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
                requestNextPageOfMovies(++page)
            }
            onPaginationInfoObtained(discover)
        }
    }

    private fun onPaginationInfoObtained(pagination: Discover) {
        page = pagination.page
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
            is NoMoreMoviesException -> {
                _state.value = state.value!!.copy(
                    noMoreMovies = true,
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