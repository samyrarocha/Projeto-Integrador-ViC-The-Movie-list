package com.example.projeto_integrador.features.feed.data.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.databinding.GenreButtonRecyclerViewBinding

class GenreRecyclerViewAdapter :
    ListAdapter<UIGenre, GenreRecyclerViewAdapter.GenreViewHolder>
        (GENRE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = GenreButtonRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {

        val item: UIGenre = getItem(position)

        holder.bind(item)
    }

    inner class GenreViewHolder(
        private val binding: GenreButtonRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UIGenre) {
            binding.genreButton.text = item.name
        }
    }
}

private val GENRE_COMPARATOR = object : DiffUtil.ItemCallback<UIGenre>() {
    override fun areItemsTheSame(oldItem: UIGenre, newItem: UIGenre): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIGenre, newItem: UIGenre): Boolean {
        return oldItem.name == newItem.name
    }
}