package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.data.stubs.getFavoriteMovies
import com.example.projeto_integrador.domain.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetFavoriteMoviesUseCaseTest{
    private lateinit var getFavoriteMovieUseCase: GetFavoriteMoviesUseCase
    private val fakeRepository = Mockito.mock(MoviesRepository::class.java)

    @Before
    fun setupUseCase(){
        getFavoriteMovieUseCase = GetFavoriteMoviesUseCase(fakeRepository)
    }

    @Test
    fun onSuccess() = runBlocking {
        //Given
        Mockito.`when`(fakeRepository.getFavoriteMovies()).thenReturn(getFavoriteMovies())

        //When
        val result = getFavoriteMovieUseCase.invoke()

        //Then
        assertEquals(result, getFavoriteMovies())
    }
}