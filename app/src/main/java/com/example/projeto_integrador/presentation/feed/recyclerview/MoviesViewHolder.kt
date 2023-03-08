package com.example.projeto_integrador.presentation.feed.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.data.api.models.ApiConstants
import com.example.projeto_integrador.databinding.RecyclerViewMovieItemBinding
import com.example.projeto_integrador.domain.model.movies.MediaSizes
import com.example.projeto_integrador.presentation.models.UIMovie
import com.squareup.picasso.Picasso

class MoviesViewHolder(
    private val binding: RecyclerViewMovieItemBinding,
    private val onClickPerformed: (movieId: Long) -> Unit,
    private val onFavoriteClickPerformed: (uiMovie: UIMovie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UIMovie) {
        binding.itemMovieTitleTextView.text = item.name

        binding.favoritedIconButton.apply {
            isChecked = item.favorite
            setOnCheckedChangeListener { _, _ ->
                onFavoriteClickPerformed(item)
            }
        }

        binding.itemMovieImageView.setOnClickListener {
            onClickPerformed(item.id)
        }

        binding.itemRatingTitleTextView.text = item.popularity.toInt().toString() + "%"
        Picasso.get()
            .load(
                ApiConstants.BASE_IMAGE_ENDPOINT
                        + MediaSizes.original
                        + item.image
            )
            .into(
                binding.itemMovieImageView
            )
    }
}