package com.example.projeto_integrador.features.feed.presentation

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto_integrador.R
import com.example.projeto_integrador.common.domain.model.NetworkUnavailableException
import com.example.projeto_integrador.common.domain.model.NoMoreMoviesException
import com.example.projeto_integrador.common.domain.model.movies.*
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.ui.UIGenre
import com.example.projeto_integrador.features.feed.data.ui.UIMovie
import com.example.projeto_integrador.features.feed.data.ui.mappers.UiGenreMapper
import com.example.projeto_integrador.features.feed.data.ui.mappers.UiMovieMapper
import com.example.projeto_integrador.features.feed.usecases.*
import com.example.projeto_integrador.features.feed.uttils.DispatchersProviderImp
import com.example.projeto_integrador.features.feed.uttils.createExceptionHandler
import com.example.projeto_integrador.features.search.data.UiSearchResultsMapper
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
    private val storeFavoriteMovieUseCase: StoreFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val dispatchersProvider: DispatchersProviderImp
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

        subscribeToMovieUpdate()
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
            is AllMoviesEvent.UpdateFavoriteMovie -> updateFavoriteMovie(event.uiMovie)
        }
    }

    fun onGenreEvent(event: GenreEvent) {
        when (event) {
            is GenreEvent.RequestGenreList -> loadGenres()
        }
    }

    private fun subscribeToMovieUpdate() {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    requestNextPageOfMoviesUseCase(page, "")
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

    fun selectGenre(genre: UIGenre) {
        if (genre == selectedGenre.value) {
            selectedGenre.value = null
        } else {
            selectedGenre.value = genre
        }

        _state.value = _state.value?.copy(loading = true)

        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    requestNextPageOfMoviesUseCase(page, selectedGenre.value?.id.toString())
                }
            }.onSuccess {
                onNewMovieList(it.movies)
            }.onFailure {
                onFailure(it)
            }
        }
    }

    private fun prepareForSearch() {
        setupSearchSubscription()
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
        _state.value = _state.value?.updateMovies(movies.map { uiMovieMapper.mapToView(it) })
    }

    fun onNewGenreList(genre: List<Genre>) {
        _state.value = _state.value?.copy(
            loading = false,
            genre = genre.map { uiGenreMapper.mapToView(it) }
        )
    }

    private fun loadMovies() {
        selectedGenre.value = null
        page = 1
        _state.value = _state.value?.copy(
            movies = emptyList()
        )

        if (state.value?.movies?.isEmpty() == true) {
            loadNextMoviePage()
        }
    }

    private fun loadGenres() {}

    private fun loadNextMoviePage() {
        val exceptionHandler = viewModelScope.createExceptionHandler(R.string.no_more_movies) {
            onFailure(it)
        }

        viewModelScope.launch(exceptionHandler) {
            val discover = withContext(dispatchersProvider.io()) {
                requestNextPageOfMoviesUseCase(page++, "")
            }
            onPaginationInfoObtained(discover)
        }
    }

    private fun onPaginationInfoObtained(pagination: Discover) {
        page = pagination.page
        _state.value = _state.value?.updateMovies(pagination.movies.map { uiMovieMapper.mapToView(it) })
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            _state.value = _state.value?.updateFavoriteMovies(getFavoriteMoviesUseCase().map { uiMovieMapper.mapToView(it) })
        }
    }

    private fun updateFavoriteMovie(uiMovie: UIMovie){
        viewModelScope.launch {
            var movie = Movie(
                discoverVoteAverage = uiMovie.popularity,
                discoverPosterPath = uiMovie.image,
                discoverMovieTitle = uiMovie.name,
                discoverMovieId = uiMovie.id,
                favorite = uiMovie.favorite
            )

            if (state.value?.favoriteMovie?.contains(uiMovie) == false){
                movie = movie.copy(favorite = true)
                storeFavoriteMovieUseCase(movie)
            }else {
                movie = movie.copy(favorite = false)
                deleteFavoriteMovieUseCase(movie)
            }

        }
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