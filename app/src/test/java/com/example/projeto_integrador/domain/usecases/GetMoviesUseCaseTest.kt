package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.data.stubs.getResultsForDiscover
import com.example.projeto_integrador.domain.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetMoviesUseCaseTest{
    private lateinit var getMoviesUseCase: GetMoviesUseCase
    private val fakeRepository = Mockito.mock(MoviesRepository::class.java)

    @Before
    fun setupUseCase(){
        getMoviesUseCase = GetMoviesUseCase(fakeRepository)
    }

    @Test
    fun onSuccess() = runBlocking {
        //Given
        Mockito.`when`(fakeRepository.getMovies()).thenReturn(getResultsForDiscover())

        //When
        val result = getMoviesUseCase.invoke()

        //Then
        assertEquals(result, getResultsForDiscover())
    }
}