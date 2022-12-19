package com.example.projeto_integrador.presentation.feed

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projeto_integrador.R
import com.example.projeto_integrador.domain.model.NetworkUnavailableException
import com.example.projeto_integrador.domain.model.NoMoreMoviesException
import com.example.projeto_integrador.domain.model.movies.*
import com.example.projeto_integrador.domain.usecases.*
import com.example.projeto_integrador.presentation.feed.models.Event
import com.example.projeto_integrador.presentation.models.UIGenre
import com.example.projeto_integrador.presentation.models.UIMovie
import com.example.projeto_integrador.domain.mappers.UiGenreMapper
import com.example.projeto_integrador.domain.mappers.UiMovieMapper
import com.example.projeto_integrador.domain.uttils.DispatchersProviderImp
import com.example.projeto_integrador.domain.uttils.createExceptionHandler
import com.example.projeto_integrador.domain.mappers.UiSearchResultsMapper
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
                _state.value = _state.value?.copy()
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
            runCatching {
                withContext(Dispatchers.IO) {
                    getFavoriteMoviesUseCase()
                }
            }.onSuccess {
                _state.value = _state.value?.updateFavoriteMovies(it.map { favoriteMovieList ->
                    uiMovieMapper.mapToView(favoriteMovieList)
                })
            }.onFailure {
                onFailure(it)
            }
        }
    }

    private fun updateFavoriteMovie(uiMovie: UIMovie){
        viewModelScope.launch {
            val movie = Movie(
                discoverVoteAverage = uiMovie.popularity,
                discoverPosterPath = uiMovie.image,
                discoverMovieTitle = uiMovie.name,
                discoverMovieId = uiMovie.id,
                favorite = uiMovie.favorite
            )

            if (state.value?.favoriteMovie?.contains(uiMovie) == false){
                uiMovie.copy(favorite = true)
                storeFavoriteMovieUseCase(movie)
            } else if (state.value?.favoriteMovie?.contains(uiMovie) == true && uiMovie.favorite){
                uiMovie.copy(favorite = false)
                deleteFavoriteMovieUseCase(movie)
            }
            //updateFavoriteItem(uiMovie)
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

    private fun updateFavoriteItem(uiMovie: UIMovie) {
        val movie = state.value?.movies
        movie?.forEachIndexed { index, favoritedMovie ->
            if (favoritedMovie.id == uiMovie.id){
                updateAllMoviesList(movie, index, uiMovie.favorite)
            }
        }
    }

    private fun updateAllMoviesList(movie: List<UIMovie>, index: Int, isFavorite: Boolean) {
        val updatedList = movie.toMutableList().apply {
            this[index] = this[index].copy(favorite = isFavorite)
        }
        _state.value = _state.value?.copy(movies = updatedList)
    }
}