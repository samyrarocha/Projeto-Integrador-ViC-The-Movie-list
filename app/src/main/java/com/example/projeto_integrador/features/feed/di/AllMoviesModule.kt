package com.example.projeto_integrador.features.feed.di

import com.example.projeto_integrador.common.data.GenreRepositoryImp
import com.example.projeto_integrador.common.data.MoviesRepositoryImp
import com.example.projeto_integrador.common.data.api.models.TmdbApi
import com.example.projeto_integrador.common.data.api.models.mappers.*
import com.example.projeto_integrador.common.data.di.ApiService
import com.example.projeto_integrador.common.domain.repositories.GenreRepository
import com.example.projeto_integrador.common.domain.repositories.MoviesRepository
import com.example.projeto_integrador.features.feed.data.ui.mappers.UiGenreMapper
import com.example.projeto_integrador.features.feed.data.ui.mappers.UiMovieMapper
import com.example.projeto_integrador.features.feed.presentation.AllMoviesViewModel
import com.example.projeto_integrador.features.feed.usecases.*
import com.example.projeto_integrador.features.feed.uttils.DispatchersProviderImp
import com.example.projeto_integrador.features.moviedetails.MovieDetailsViewModel
import com.example.projeto_integrador.features.moviedetails.data.ui.mapper.UiCastMapper
import com.example.projeto_integrador.features.moviedetails.data.ui.mapper.UiCrewMapper
import com.example.projeto_integrador.features.moviedetails.data.ui.mapper.UiMovieDetailsMapper
import com.example.projeto_integrador.features.moviedetails.usecase.GetMovieDetailsUseCase
import com.example.projeto_integrador.features.search.data.UiSearchResultsMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal val allMoviesModule = module {

    //Service
    factory { ApiService(retrofit = get()).createService(TmdbApi::class.java)}

    factory<MoviesRepository> {
        MoviesRepositoryImp(
            api = get(),
            apiDiscoverMapper =  ApiDiscoverMapper(apiMovieMapper = ApiMovieMapper()),
            apiSearchMapper = ApiSearchMapper(apiSearchResultsMapper = ApiSearchResultsMapper()),
            apiMovieDetailsMapper = ApiMovieDetailsMapper(
                apiCreditsMapper = ApiCreditsMapper(
                    apiCastMapper = ApiCastMapper(),
                    apiCrewMapper = ApiCrewMapper()
                ),
            apiGenreMapper = ApiGenreMapper()),
            cache = get()

        )
    }

    factory<GenreRepository> {
        GenreRepositoryImp(
            api = get(),
            apiGenreMapper = ApiGenreMapper()
        )
    }

    //Use cases
    factory {
        RequestNextPageOfMoviesUseCase(get())
    }

    factory {
        GenreListUseCase(get())
    }

    factory {
        GetMovieDetailsUseCase(get())
    }

    factory {
        SearchMoviesUseCase(get())
    }

    factory {
        GetFavoriteMoviesUseCase(get())
    }

    single { DispatchersProviderImp() }

    viewModel {
        AllMoviesViewModel(
            uiMovieMapper = UiMovieMapper(),
            uiGenreMapper = UiGenreMapper(),
            uiSearchResultsMapper = UiSearchResultsMapper(),
            searchMoviesUseCase = SearchMoviesUseCase(get()),
            requestNextPageOfMoviesUseCase = get(),
            genreListUseCase = get(),
            dispatchersProvider = get(),
            getFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(get()),
            storeFavoriteMovieUseCase = StoreFavoriteMovieUseCase(get()),
            deleteFavoriteMovieUseCase =  DeleteFavoriteMovieUseCase(get())
        )
    }

    viewModel {
        MovieDetailsViewModel(
            uiMovieDetailsMapper = UiMovieDetailsMapper(
                UiGenreMapper(),
                UiCrewMapper(),
                UiCastMapper()),
            dispatchersProvider = get(),
            getMovieDetailsUseCase = GetMovieDetailsUseCase(get())
        )
    }

}


internal val allMoviesDependencies by lazy {
    loadKoinModules(allMoviesModule)
}

fun initAllMoviesDependencies() = allMoviesDependencies


