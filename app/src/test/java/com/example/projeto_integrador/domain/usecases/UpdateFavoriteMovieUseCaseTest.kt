package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.data.stubs.updateFavoriteMovie
import com.example.projeto_integrador.domain.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class UpdateFavoriteMovieUseCaseTest{
    private lateinit var updateFavoriteMovieUseCase: UpdateFavoriteMovieUseCase
    private val fakeRepository = Mockito.mock(MoviesRepository::class.java)

    @Before
    fun setupUseCase(){
        updateFavoriteMovieUseCase = UpdateFavoriteMovieUseCase(fakeRepository)
    }

    @Test
    fun onSuccess() = runBlocking {
        //Given
        val movie = updateFavoriteMovie()
        Mockito.`when`(fakeRepository.updateFavoriteMovie(updateFavoriteMovie()))
            .thenReturn(Unit)

        //When
        val result = updateFavoriteMovieUseCase.invoke(movie)

        //Then
        assertEquals(result, Unit)
    }

}