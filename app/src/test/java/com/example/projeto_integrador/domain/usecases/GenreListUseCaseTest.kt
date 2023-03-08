package com.example.projeto_integrador.domain.usecases

import com.example.projeto_integrador.data.stubs.getGenreList
import com.example.projeto_integrador.domain.model.NoMoreItemsException
import com.example.projeto_integrador.domain.model.movies.Genre
import com.example.projeto_integrador.domain.repository.GenreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GenreListUseCaseTest {

    private lateinit var genreListUseCase: GenreListUseCase
    private val fakeRepository = mock(GenreRepository::class.java)

    @Before
    fun setupUseCase(){
        genreListUseCase = GenreListUseCase(fakeRepository)
    }

    @Test
    fun onSuccess() = runBlocking {
        //Given
        `when`(fakeRepository.requestGenreList()).thenReturn(getGenreList())

        //When
        val result = genreListUseCase.invoke()

        //Then
        assertEquals(result, getGenreList())
    }

    @Test(expected = NoMoreItemsException::class)
    fun onFailure() = runBlocking {
        //Given
        val emptyGenreList = emptyList<Genre>()
        `when`(fakeRepository.requestGenreList()).thenReturn(emptyGenreList)

        //When
        val fakeException = genreListUseCase.invoke()

        //Then
        assertEquals("Empty genre list :(", fakeException)
    }


}