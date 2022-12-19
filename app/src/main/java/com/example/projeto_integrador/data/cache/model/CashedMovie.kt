package com.example.projeto_integrador.data.cache.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.projeto_integrador.domain.model.movies.Movie

@Entity(
    tableName = "favoriteMovies",
    foreignKeys = [
        ForeignKey(
            entity = CachedFavoriteMovies::class,
            parentColumns = ["movieId"],
            childColumns = ["movieId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("movieId")]
)
data class CachedFavoriteMovies(
    @PrimaryKey (autoGenerate = true)
    val movieId: Long,
    val favorite: Boolean
)

@Entity(
    tableName = "movies",
    foreignKeys = [
        ForeignKey(
            entity = CachedMovie::class,
            parentColumns = ["movieId"],
            childColumns = ["movieId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("movieId")]
)
data class CachedMovie(
    @PrimaryKey val movieId: Long,
    val movieTitle: String,
    val popularity: Float,
    val posterPath: String,
    val favorite: Boolean

){
    fun toDomain(): Movie = Movie(
        discoverMovieId = movieId,
        discoverMovieTitle = movieTitle,
        discoverPosterPath = posterPath,
        discoverVoteAverage = popularity,
        favorite = favorite
    )
}
