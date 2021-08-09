package com.example.projeto_integrador.features.feed.data.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.common.data.api.models.ApiConstants
import com.example.projeto_integrador.common.data.api.models.ApiDiscover
import com.example.projeto_integrador.common.data.api.models.MovieResponse
import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.databinding.RecyclerViewMovieItemBinding
import com.squareup.picasso.Picasso
import retrofit2.Retrofit


class AllMoviesRecyclerViewAdapter(val picasso: Picasso) :
    ListAdapter<UIMovie, AllMoviesRecyclerViewAdapter.MoviesViewHolder>
        (ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = RecyclerViewMovieItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {

        val item: UIMovie = getItem(position)

        holder.bind(item)
    }

    inner class MoviesViewHolder(
        private val binding: RecyclerViewMovieItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UIMovie) {
            binding.itemMovieTitleTextView.text = item.name
            picasso.load(ApiConstants.BASE_IMAGE_ENDPOINT + "original" + item.image).into(binding.itemMovieImageView)
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

