package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.data.stubs.getResultsForLocalSearch
import com.example.projeto_integrador.data.stubs.getResultsForSearch
import com.example.projeto_integrador.domain.model.NoSearchResultsException
import com.example.projeto_integrador.domain.repository.MoviesRepository
import com.example.projeto_integrador.presentation.feed.models.SearchParameters
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class SearchMoviesUseCaseTest{
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase
    private val fakeRepository = Mockito.mock(MoviesRepository::class.java)

    @Before
    fun setupUseCase(){
        searchMoviesUseCase = SearchMoviesUseCase(fakeRepository)
    }

    @Test
    fun searchRemote_Success() = runBlocking {
        //Given
        val pageToLoad = 1
        val searchQuery = "wakanda"
        val repositorySearchParameters = SearchParameters(query = "wakanda")
        `when`(fakeRepository.searchMoviesLocally("wakanda%")).thenReturn(emptyList())
        `when`(fakeRepository.searchMoviesRemote(pageToLoad, repositorySearchParameters))
            .thenReturn(getResultsForSearch())

        //When
        val result = searchMoviesUseCase.invoke(searchQuery)

        //Then
        assertEquals(result, getResultsForSearch())
    }

    @Test
    fun searchLocal_Success() = runBlocking {
        //Given
        val searchQuery = "wakanda"
        `when`(fakeRepository.searchMoviesLocally("wakanda%"))
            .thenReturn(getResultsForLocalSearch())

        //When
        val result = searchMoviesUseCase.invoke(searchQuery)

        //Then
        assertEquals(result, getResultsForSearch())
    }

    @Test(expected = NoSearchResultsException::class)
    fun onFailure() = runBlocking {
        //Given
        val searchQuery = "wakanda"
        val pageToLoad = 1
        val repositorySearchParameters = SearchParameters(query = "wakanda")
        `when`(fakeRepository.searchMoviesLocally("wakanda%")).thenReturn(emptyList())
        `when`(fakeRepository.searchMoviesRemote(pageToLoad, repositorySearchParameters))
            .thenReturn(getResultsForSearch().copy(searchResults = emptyList()))

        //When
        val fakeException = searchMoviesUseCase.invoke(searchQuery)

        //Then
        assertEquals("No search results :(", fakeException)
    }
}