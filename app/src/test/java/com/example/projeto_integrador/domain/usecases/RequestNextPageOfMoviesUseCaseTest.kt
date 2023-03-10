package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.data.stubs.getDiscoverStub
import com.example.projeto_integrador.domain.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RequestNextPageOfMoviesUseCaseTest {
    private lateinit var requestNextPageOfMoviesUseCase: RequestNextPageOfMoviesUseCase
    private val fakeRepository = Mockito.mock(MoviesRepository::class.java)

    @Before
    fun setupUseCase(){
        requestNextPageOfMoviesUseCase = RequestNextPageOfMoviesUseCase(fakeRepository)
    }

    @Test
    fun onSuccess() = runBlocking {
        //Given
        val pageToLoad = 1
        val genreId = ""
        Mockito.`when`(fakeRepository.requestMovies(pageToLoad, genreId))
            .thenReturn(getDiscoverStub())

        //When
        val result = requestNextPageOfMoviesUseCase.invoke(pageToLoad, genreId)

        //Then
        assertEquals(result, getDiscoverStub())
    }

}