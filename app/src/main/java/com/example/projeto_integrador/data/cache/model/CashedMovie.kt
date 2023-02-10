package com.example.projeto_integrador.data.cache.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.projeto_integrador.domain.model.movies.Movie

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
    @PrimaryKey
    val movieId: Long,
    val movieTitle: String,
    val popularity: Float,
    val posterPath: String,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean

){
    fun toDomain(): Movie = Movie(
        discoverMovieId = movieId,
        discoverMovieTitle = movieTitle,
        discoverPosterPath = posterPath,
        discoverVoteAverage = popularity,
        favorite = favorite
    )

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
