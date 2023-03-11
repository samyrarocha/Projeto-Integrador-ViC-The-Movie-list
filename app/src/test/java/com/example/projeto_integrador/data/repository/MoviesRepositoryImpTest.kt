package com.example.projeto_integrador.data.repository

import com.example.projeto_integrador.data.api.models.ApiParameterValues
import com.example.projeto_integrador.data.api.models.ApiParameters
import com.example.projeto_integrador.data.api.models.TmdbApi
import com.example.projeto_integrador.data.cache.Cache
import com.example.projeto_integrador.data.mappers.ApiDiscoverMapper
import com.example.projeto_integrador.data.mappers.ApiMovieDetailsMapper
import com.example.projeto_integrador.data.mappers.ApiSearchMapper
import com.example.projeto_integrador.data.stubs.*
import com.example.projeto_integrador.presentation.feed.models.SearchParameters
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*

class MoviesRepositoryImpTest{


    private lateinit var fakeMoviesRepositoryImp: MoviesRepositoryImp

    @Mock
    private val fakeApiDiscoverMapper = mock(ApiDiscoverMapper::class.java)
    private val fakeApiSearchMapper = mock(ApiSearchMapper::class.java)
    private val fakeApiMovieDetailsMapper = mock(ApiMovieDetailsMapper::class.java)
    private val fakeApi = mock(TmdbApi::class.java)
    private val fakeCache = mock(Cache::class.java)

    @Before
    fun setupRepository() {
        fakeMoviesRepositoryImp = MoviesRepositoryImp(
            fakeApi,
            fakeApiDiscoverMapper,
            fakeApiSearchMapper,
            fakeApiMovieDetailsMapper,
            fakeCache
        )
    }

    @After
    fun tearDown(){
        clearAllCaches()
    }

    @Test
    fun requestMovie_successful() = runBlocking {
        //Given
        val pageToLoad = 1
        `when`(fakeApi.getDiscover(pageToLoad, null, ApiParameterValues.LANGUAGE_VALUE))
            .thenReturn(getApiDiscoverStub())
        `when`(fakeApiDiscoverMapper.mapToDomain(getApiDiscoverStub()))
            .thenReturn(getDiscoverStub())

        //When
        val result = fakeMoviesRepositoryImp.requestMovies(pageToLoad, null)

        //Then
        assertEquals(result, getDiscoverStub())
    }

    @Test
    fun searchMovies_successful() = runBlocking {
        //Given
        val pageToLoad = 1
        val searchParameters = "wakanda"
        val repositorySearchParameters = SearchParameters(query = "wakanda")
        `when`(fakeApi.getSearch(pageToLoad, searchParameters, ""))
            .thenReturn(getResultsForApiSearch())
        `when`(fakeApiSearchMapper.mapToDomain(getResultsForApiSearch()))
            .thenReturn(getResultsForSearch())

        //When
        val result = fakeMoviesRepositoryImp.searchMoviesRemote(pageToLoad, repositorySearchParameters)

        //Then
        assertEquals(result, getResultsForSearch())
    }

    @Test
    fun getMovieDetails_successful() = runBlocking {
        //Given
        val movieId: Long = 550
        `when`(fakeApi.getMovieDetails(
            movieId = movieId,
            dataLanguage = ApiParameterValues.LANGUAGE_VALUE,
            appendToResponse = ApiParameters.CREDITS)
        ).thenReturn(getApiMovieDetails())
        `when`(fakeApiMovieDetailsMapper.mapToDomain(getApiMovieDetails()))
            .thenReturn(getMovieDetails())

        //When
        val result = fakeMoviesRepositoryImp.getMovieDetails(movieId)

        //Then
        assertEquals(result, getMovieDetails())
    }
}