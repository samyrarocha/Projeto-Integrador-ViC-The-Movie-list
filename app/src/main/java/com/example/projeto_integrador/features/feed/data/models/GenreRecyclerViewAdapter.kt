package com.example.projeto_integrador.features.feed.data.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.common.domain.model.movies.details.MovieWithDetails
import com.example.projeto_integrador.databinding.GenreButtonRecyclerViewBinding
import com.example.projeto_integrador.features.feed.data.ui.UIGenre

class GenreRecyclerViewAdapter(
    private val onClickPerformed: (selectedGenre: UIGenre) -> Unit
) :
    ListAdapter<UIGenre, GenreRecyclerViewAdapter.GenreViewHolder>
        (GENRE_COMPARATOR) {

    var selectedGenre: UIGenre? = null

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
            binding.genreButton.isSelected = selectedGenre == item
            binding.genreButton.setOnClickListener {
                binding.genreButton.textColors.defaultColor
                onClickPerformed(item)
            }
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