package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.data.stubs.getMovieDetails
import com.example.projeto_integrador.domain.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetMovieDetailsUseCaseTest{
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private val fakeRepository = Mockito.mock(MoviesRepository::class.java)

    @Before
    fun setupUseCase(){
        getMovieDetailsUseCase = GetMovieDetailsUseCase(fakeRepository)
    }

    @Test
    fun onSuccess() = runBlocking {
        //Given
        val movieId: Long = 550
        Mockito.`when`(fakeRepository.getMovieDetails(movieId)).thenReturn(getMovieDetails())

        //When
        val result = getMovieDetailsUseCase.invoke(movieId)

        //Then
        assertEquals(result, getMovieDetails())
    }

}