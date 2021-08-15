package com.example.projeto_integrador.features.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projeto_integrador.features.feed.data.ui.mappers.UiMovieMapper
import com.example.projeto_integrador.features.feed.uttils.DispatchersProvider
import com.example.projeto_integrador.features.search.domain.usecase.SearchMovies
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.*

class SearchViewModel (
    private val searchMovies: SearchMovies,
    private val uiMovieMapper: UiMovieMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: ArrayCompositeDisposable
        ): ViewModel() {

    val _state: MutableLiveData<SearchViewState> = MutableLiveData()
    val state: LiveData<SearchViewState> get() = _state
    private val querySubject = BehaviorSubject.create<String>()

    private var searchJob: Job = Job()
    private var currentPage = 0

    init {
        _state.value = SearchViewState()
    }

//    private fun createExceptionHandler(message: String): CoroutineExceptionHandler {
//        return viewModelScope.createExceptionHandler(R.string.no_more_movies) {
//            onFailure(it)
//        }
//    }

//    private fun searchMovies(searchParameters: SearchParameters){
//        val exceptionHandler = createExceptionHandler(message = "Esse Filme não existe")
//
//        searchJob = viewModelScope.launch(exceptionHandler) {
//            val moviePage = withContext(dispatchersProvider.io()) {
//                searchMovies(++currentPage, searchParameters)
//            }
//        }
//        searchJob.invokeOnCompletion { it?.printStackTrace() }
//    }

//    private fun onFailure(throwable: Throwable) {
//        _state.value = if (throwable is NoMoreMoviesException) {
//            state.value!!.updateToNoResultsAvailable()
//        } else {
//            state.value!!.updateToHasFailure(throwable)
//        }



}