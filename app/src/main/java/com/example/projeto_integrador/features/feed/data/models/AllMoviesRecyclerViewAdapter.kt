package com.example.projeto_integrador.features.feed.data.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.common.data.api.models.ApiConstants
import com.example.projeto_integrador.common.domain.model.movies.MediaSizes
import com.example.projeto_integrador.databinding.RecyclerViewMovieItemBinding
import com.example.projeto_integrador.features.feed.data.ui.UIMovie
import com.squareup.picasso.Picasso


class AllMoviesRecyclerViewAdapter(
    private val onClickPerformed: (movieId: Long) -> Unit,
    private val onFavoriteClickPerformed: (uiMovie: UIMovie) -> Unit
) :
    ListAdapter<UIMovie, AllMoviesRecyclerViewAdapter.MoviesViewHolder>
        (ITEM_COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MoviesViewHolder {
        val binding = RecyclerViewMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {

        val item: UIMovie = getItem(position)

        holder.bind(item)
    }

    inner class MoviesViewHolder(
        private val binding: RecyclerViewMovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: UIMovie) {
            binding.itemMovieTitleTextView.text = item.name

            if (item.favorite) {
                binding.NotFavoritedImageView.isVisible = false
                binding.FavoritedImageView.isVisible = true
            } else {
                binding.NotFavoritedImageView.isVisible = true
                binding.FavoritedImageView.isVisible = false
            }

            binding.NotFavoritedImageView.setOnClickListener{
                onFavoriteClickPerformed(item)
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
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UIMovie>() {
    override fun areItemsTheSame(oldItem: UIMovie, newItem: UIMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIMovie, newItem: UIMovie): Boolean {
        return oldItem.name == newItem.name && oldItem.image == newItem.image
    }
}

