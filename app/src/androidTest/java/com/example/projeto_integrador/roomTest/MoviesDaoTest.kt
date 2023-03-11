package com.example.projeto_integrador.roomTest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.projeto_integrador.data.cache.MovieDatabase
import com.example.projeto_integrador.data.cache.daos.MoviesDao
import com.example.projeto_integrador.stubs.*
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
@SmallTest
class MoviesDaoTest {

    private lateinit var movieDatabase: MovieDatabase
    private lateinit var moviesDao: MoviesDao

    @Before
    fun setupDatabase() {
        movieDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()

        moviesDao = movieDatabase.moviesDao()
    }

    @After
    fun closeDatabase() {
        movieDatabase.close()
    }

    @Test
    fun insertCachedMovie_returnsSuccess() = runBlocking {
        //Given
        val movieList = moviesListStub()

        //When
        moviesDao.storeNewCacheData(movieList)

        //Then
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            assert(moviesDao.getMovies().contains(firstMovieStub()))
            assert(moviesDao.getMovies().contains(secondMovieStub()))
            latch.countDown()
        }
        withContext(Dispatchers.IO) {
            latch.await()
        }
        job.cancelAndJoin()
    }

    @Test
    fun updateFavoriteMovie_returnsSuccess() = runBlocking{
        //Given
        moviesDao.storeNewCacheData(moviesListStub())

        //When
        moviesDao.updateFavoriteMovie(changedSecondMovieStub())

        //Then
        assert(moviesDao.getMovies().contains(changedSecondMovieStub()))
        assert(!moviesDao.getMovies().contains(secondMovieStub()))
    }

    @Test
    fun getFavoriteMovies_returnsSuccess() = runBlocking{
        //Given
        moviesDao.storeNewCacheData(moviesListStub())

        //When
        moviesDao.getFavoriteMovies()

        //Then
        assert(moviesDao.getFavoriteMovies().contains(firstMovieStub()))
        assert(!moviesDao.getFavoriteMovies().contains(secondMovieStub()))
    }

    @Test
    fun searchMovies_returnsSuccess() = runBlocking {
        //Given
        val query = "Bl%"
        moviesDao.storeNewCacheData(moviesListStub())

        //When
        val result = moviesDao.searchMovie(query)

        //Then
        assertEquals(searchResults(), result)
    }

}