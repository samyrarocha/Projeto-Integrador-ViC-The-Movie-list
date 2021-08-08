package com.example.projeto_integrador.features.feed.data.models

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.common.domain.model.movies.Discover
import com.example.projeto_integrador.databinding.RecyclerViewMovieItemBinding
import com.example.projeto_integrador.features.feed.presentation.AllMoviesFragment
import com.example.projeto_integrador.features.feed.presentation.AllMoviesFragmentViewModel


class AllMoviesRecyclerViewAdapter:
    RecyclerView.Adapter<AllMoviesRecyclerViewAdapter.MoviesViewHolder>() {

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
        private val binding: RecyclerViewMovieItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UIMovie) {
            //binding.name.text = item.name
           // binding.itemMovieImageView.setImageResource(item.image)
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        var itemCount =
    }

}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UIMovie>() {
    override fun areItemsTheSame(oldItem: UIMovie, newItem: UIMovie): Boolean{
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIMovie, newItem: UIMovie): Boolean {
        return oldItem.name == newItem.name && oldItem.image == newItem.image
    }
}

