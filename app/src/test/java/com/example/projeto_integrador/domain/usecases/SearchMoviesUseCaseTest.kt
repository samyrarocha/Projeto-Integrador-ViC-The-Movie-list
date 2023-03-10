package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.data.stubs.getResultsForSearch
import com.example.projeto_integrador.domain.repository.MoviesRepository
import com.example.projeto_integrador.presentation.feed.models.SearchParameters
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class SearchMoviesUseCaseTest{
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase
    private val fakeRepository = Mockito.mock(MoviesRepository::class.java)

    @Before
    fun setupUseCase(){
        searchMoviesUseCase = SearchMoviesUseCase(fakeRepository)
    }

    @Test
    fun onSuccess() = runBlocking {
        //Given
        val pageToLoad = 1
        val searchQuery = "wakanda"
        val repositorySearchParameters = SearchParameters(query = "wakanda")
        Mockito.`when`(fakeRepository.searchMovies(pageToLoad, repositorySearchParameters))
            .thenReturn(getResultsForSearch())

        //When
        val result = searchMoviesUseCase.invoke(searchQuery)

        //Then
        assertEquals(result, getResultsForSearch())
    }
}