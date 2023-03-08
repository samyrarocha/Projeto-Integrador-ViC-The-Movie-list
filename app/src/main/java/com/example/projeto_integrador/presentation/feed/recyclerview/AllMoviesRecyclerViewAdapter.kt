package com.example.projeto_integrador.presentation.feed.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.projeto_integrador.databinding.RecyclerViewMovieItemBinding
import com.example.projeto_integrador.presentation.models.UIMovie


class AllMoviesRecyclerViewAdapter(
    private val onClickPerformed: (movieId: Long) -> Unit,
    private val onFavoriteClickPerformed: (uiMovie: UIMovie) -> Unit
) :ListAdapter<UIMovie, MoviesViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MoviesViewHolder {
        val binding = RecyclerViewMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return MoviesViewHolder(binding, onClickPerformed, onFavoriteClickPerformed)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {

        val item: UIMovie = getItem(position)

        holder.bind(item)
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

