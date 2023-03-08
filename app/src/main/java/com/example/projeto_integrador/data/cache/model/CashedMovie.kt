package com.example.projeto_integrador.data.cache.model

import androidx.room.*
import com.example.projeto_integrador.domain.model.movies.Movie

@Entity(
    tableName = "movies",
    indices = [Index("movieId")]
)
data class CachedMovie(
    @PrimaryKey(autoGenerate = false)
    val movieId: Long,
    val movieTitle: String,
    val popularity: Float,
    val posterPath: String,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean
){

    companion object {
        fun fromDomain(movie: Movie): CachedMovie = CachedMovie(
                movieId = movie.discoverMovieId,
                movieTitle = movie.discoverMovieTitle,
                popularity = movie.discoverVoteAverage,
                favorite = movie.favorite,
                posterPath = movie.discoverPosterPath
            )
        }
}
