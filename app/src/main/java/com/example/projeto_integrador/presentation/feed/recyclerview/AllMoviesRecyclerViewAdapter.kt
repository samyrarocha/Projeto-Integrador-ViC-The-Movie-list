package com.example.projeto_integrador.presentation.feed.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.data.api.models.ApiConstants
import com.example.projeto_integrador.domain.model.movies.MediaSizes
import com.example.projeto_integrador.databinding.RecyclerViewMovieItemBinding
import com.example.projeto_integrador.presentation.models.UIMovie
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
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UIMovie>() {
    override fun areItemsTheSame(oldItem: UIMovie, newItem: UIMovie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIMovie, newItem: UIMovie): Boolean {
        return oldItem.name == newItem.name && oldItem.image == newItem.image
    }
}

