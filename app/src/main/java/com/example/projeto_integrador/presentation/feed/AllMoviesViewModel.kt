package com.example.projeto_integrador.presentation.feed

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto_integrador.domain.model.NetworkUnavailableException
import com.example.projeto_integrador.domain.model.NoMoreItemsException
import com.example.projeto_integrador.domain.model.movies.*
import com.example.projeto_integrador.domain.usecases.*
import com.example.projeto_integrador.presentation.feed.models.Event
import com.example.projeto_integrador.presentation.models.UIGenre
import com.example.projeto_integrador.presentation.models.UIMovie
import com.example.projeto_integrador.domain.mappers.UiGenreMapper
import com.example.projeto_integrador.domain.mappers.UiMovieMapper
import com.example.projeto_integrador.domain.mappers.UiSearchResultsMapper
import com.example.projeto_integrador.domain.model.NoSearchResultsException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class AllMoviesViewModel(
    private val uiMovieMapper: UiMovieMapper,
    private val uiGenreMapper: UiGenreMapper,
    private val uiSearchResultsMapper: UiSearchResultsMapper,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val requestNextPageOfMoviesUseCase: RequestNextPageOfMoviesUseCase,
    private val genreListUseCase: GenreListUseCase,
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateFavoriteMovieUseCase: UpdateFavoriteMovieUseCase,
): ViewModel() {

    companion object {
        const val UI_PAGE_SIZE = 20
    }

    private val _state = MutableLiveData<AllMoviesViewState>()
    val state: LiveData<AllMoviesViewState> = _state

    var isLoadingMoreMovies: Boolean = false
    var isLastPage = false
    private val querySubject = BehaviorSubject.create<String>()
    private var compositeDisposable = CompositeDisposable()


    var selectedGenre = MutableLiveData<UIGenre?>()


    private var page = 1

    init {
        _state.value = AllMoviesViewState(loading = true)
        loadMovies()
        subscribeToGenreUpdate()
    }

    fun onMoviesEvent(event: AllMoviesEvent) {
        when (event) {
            is AllMoviesEvent.RequestInitialMoviesList -> loadMovies()
            is AllMoviesEvent.RequestMoreMovies -> loadNextMoviePage()
            is AllMoviesEvent.UpdateGenre -> selectGenre(event.selectedGenre)
            is AllMoviesEvent.PrepareForSearch -> prepareForSearch()
            is AllMoviesEvent.QueryInput -> querySubject.onNext(event.input)
            is AllMoviesEvent.GetFavoriteMovies -> getFavoriteMovies()
            is AllMoviesEvent.UpdateFavoriteMovie -> updateCache(event.uiMovie)
        }
    }

    fun onGenreEvent(event: GenreEvent) {
        when (event) {
            is GenreEvent.RequestGenreList -> loadGenres()
        }
    }

    private fun subscribeToMovieUpdate(genreId: String = "") {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    requestNextPageOfMoviesUseCase(page, genreId)
                }
            }.onSuccess {
                onNewMovieList(it.movies)
            }.onFailure {
                onFailure(it)
            }
        }
    }

    private fun subscribeToGenreUpdate() {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    genreListUseCase()
                }
            }.onSuccess {
                onNewGenreList(it)
            }.onFailure {
                onFailure(it)
            }
        }
    }

    private fun selectGenre(genre: UIGenre) {
        if (genre == selectedGenre.value) {
            selectedGenre.value = null
        } else {
            selectedGenre.value = genre
        }
        subscribeToMovieUpdate(selectedGenre.value?.id.toString())
    }

    private fun prepareForSearch() {
        setupSearchSubscription()
        _state.value = state.value?.copy(searchingMovies = true)
    }


    private fun setupSearchSubscription() {
        querySubject
            .debounce(500L, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .subscribe {
                viewModelScope.launch {
                    runCatching {
                        withContext(Dispatchers.IO) {
                            searchMoviesUseCase(it)
                        }
                    }.onSuccess {
                        onSearchResults(it)
                    }.onFailure {
                        onFailure(it)
                    }
                }
            }
            .addTo(compositeDisposable)
    }

    private fun onSearchResults(search: Search) {
        onSearchMovieList(search.searchResults)
    }

    private fun onSearchMovieList(searchResults: List<SearchResults>) {
        _state.value =
            state.value?.updateToHasSearchResults(searchResults.map {
                uiSearchResultsMapper.mapToView(it) })
    }


    private fun onNewMovieList(movies: List<Movie>) {
        val uiMovieList = movies.map { uiMovieMapper.mapToView(it) }
        _state.value = state.value?.copy(
            loading = false,
            movies = uiMovieList
        )
    }

    fun onNewGenreList(genre: List<Genre>) {
        _state.value = state.value?.copy(
            loading = false,
            genre = genre.map { uiGenreMapper.mapToView(it) }
        )
    }

    private fun loadMovies() {
        selectedGenre.value = null
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO){
                    getMoviesUseCase.invoke()
                }
            }.onSuccess {
                if (it.isEmpty()){
                    subscribeToMovieUpdate()
                } else {
                    onNewMovieList(it)
                }
            }
        }
    }

    private fun loadGenres() {}

    private fun loadNextMoviePage() {
        viewModelScope.launch() {
            runCatching {
                withContext(Dispatchers.IO){
                    requestNextPageOfMoviesUseCase(page++, "")
                }
            }.onSuccess {
                onPaginationInfoObtained(it)
                onNewMovieList(it.movies)
            }.onFailure {
                onFailure(it)
            }
        }
    }

    private fun onPaginationInfoObtained(pagination: Discover) {
        page = pagination.page
        isLastPage = page == pagination.totalPages
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    getFavoriteMoviesUseCase()
                }
            }.onSuccess {
                _state.value = state.value?.updateFavoriteMovies(it.map { favoriteMovieList ->
                    uiMovieMapper.mapToView(favoriteMovieList)
                })
            }.onFailure {
                onFailure(it)
            }
        }
    }

    private fun updateCache(uiMovie: UIMovie){
        viewModelScope.launch {
            updateFavoriteMovieUseCase(
                uiMovie.mapToDomain().copy(favorite = uiMovie.favorite.not())
            )
        }
    }

    private fun UIMovie.mapToDomain(): Movie {
        return Movie(
            discoverMovieId = id,
            discoverMovieTitle = name,
            discoverPosterPath = image,
            discoverVoteAverage = popularity,
            favorite = favorite
        )
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkErrorException,
            is NetworkUnavailableException,
            is NoSearchResultsException-> {
                _state.value = state.value?.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
            is NoMoreItemsException -> {
                _state.value = state.value?.copy(
                    loading = false,
                    noMoreMovies = true,
                    failure = Event(failure)
                )
            }
        }
    }
}