package com.example.projeto_integrador.features.moviedetails.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.common.data.api.models.ApiGenre
import com.example.projeto_integrador.common.data.api.models.ApiMovieDetails
import com.example.projeto_integrador.databinding.DetailsGenreRecyclerViewBinding
import com.example.projeto_integrador.features.feed.data.ui.UIDetails
import com.example.projeto_integrador.features.feed.data.ui.UIGenre
import org.koin.core.component.getScopeName

class DetailsGenreRecyclerViewAdapter() :
    ListAdapter<ApiGenre, DetailsGenreRecyclerViewAdapter.DetailsGenreViewHolder>
        (GENRE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsGenreViewHolder {
        val binding = DetailsGenreRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

        return DetailsGenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailsGenreViewHolder, position: Int) {

        val item: ApiGenre = getItem(position)

        holder.bind(item)
    }

    inner class DetailsGenreViewHolder(
        private val binding: DetailsGenreRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ApiGenre)  {
            binding.detailsGenreCard.text = item.genreName
        }
    }
}

private val GENRE_COMPARATOR = object : DiffUtil.ItemCallback<ApiGenre>() {
    override fun areItemsTheSame(oldItem: ApiGenre, newItem: ApiGenre): Boolean {
        return oldItem.genreId == newItem.genreId
    }

    override fun areContentsTheSame(oldItem: ApiGenre, newItem: ApiGenre): Boolean {
        return oldItem.genreId == newItem.genreId
    }
}