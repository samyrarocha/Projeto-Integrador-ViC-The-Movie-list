package com.example.projeto_integrador.data.cache.daos

import androidx.room.*
import com.example.projeto_integrador.data.cache.model.CachedMovie

@Dao
abstract class MoviesDao {
    @Transaction
    @Query("SELECT * FROM movies ORDER BY movieId DESC")
    abstract suspend fun getMovies(): List<CachedMovie>

    @Transaction
    @Query("SELECT * FROM movies WHERE favorite = 1")
    abstract suspend fun getFavoriteMovies(): List<CachedMovie>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateFavoriteMovie(movie: CachedMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCachedMovie(movie: CachedMovie)

    suspend fun storeNewCacheData(movies: List<CachedMovie>) {
        for (movie in movies) {
            insertCachedMovie(movie)
        }
    }
}