package com.example.projeto_integrador.features.feed.data.cache.daos

import androidx.room.*
import com.example.projeto_integrador.features.feed.data.cache.model.CachedMovie

@Dao
abstract class MoviesDao {
    @Transaction
    @Query("SELECT * FROM movies ORDER BY movieId DESC")
    abstract suspend fun getMovies(): List<CachedMovie>

    @Delete
    abstract suspend fun deleteMovie(movie: CachedMovie)
//    @Query("SELECT * FROM favoriteMovies")
//    abstract fun getFavoriteMovies(): Flowable<List<CachedFavoriteMovies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertFavoriteMovie(movie: CachedMovie)
}