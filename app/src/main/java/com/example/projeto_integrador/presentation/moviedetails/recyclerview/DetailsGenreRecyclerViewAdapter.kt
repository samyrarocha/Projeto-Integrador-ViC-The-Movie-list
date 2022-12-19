package com.example.projeto_integrador.presentation.moviedetails.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.databinding.DetailsGenreRecyclerViewBinding
import com.example.projeto_integrador.presentation.models.UIGenre

class DetailsGenreRecyclerViewAdapter :
    ListAdapter<UIGenre, DetailsGenreRecyclerViewAdapter.DetailsGenreViewHolder>
        (GENRE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsGenreViewHolder {
        val binding = DetailsGenreRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

        return DetailsGenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsGenreViewHolder, position: Int) {

        val item: UIGenre = getItem(position)

        holder.bind(item)
    }

    inner class DetailsGenreViewHolder(
        private val binding: DetailsGenreRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UIGenre)  {
            binding.detailsGenreCard.text = item.name
        }
    }
}

private val GENRE_COMPARATOR = object : DiffUtil.ItemCallback<UIGenre>() {
    override fun areItemsTheSame(oldItem: UIGenre, newItem: UIGenre): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIGenre, newItem: UIGenre): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }
}