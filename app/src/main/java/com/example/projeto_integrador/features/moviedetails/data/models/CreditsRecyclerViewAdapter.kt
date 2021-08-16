package com.example.projeto_integrador.features.moviedetails.data.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.common.data.api.models.ApiConstants
import com.example.projeto_integrador.common.domain.model.movies.MediaSizes
import com.example.projeto_integrador.databinding.CastAndCrewRecyclerViewBinding
import com.example.projeto_integrador.features.feed.data.ui.UICredits
import com.squareup.picasso.Picasso


class CreditsRecyclerViewAdapter():
    ListAdapter<UICredits, CreditsRecyclerViewAdapter.CreditsViewHolder>
        (ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder {
        val binding = CastAndCrewRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)

        return CreditsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CreditsViewHolder, position: Int) {

        val item: UICredits = getItem(position)

        holder.bind(item)
    }

    inner class CreditsViewHolder(
        private val binding: CastAndCrewRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UICredits) {
            binding.castAndCrewNameTextView.text = item.creditsName
            binding.castAndCrewJobTextView.text = item.creditsJob
            Picasso.get()
                .load(
                ApiConstants.BASE_IMAGE_ENDPOINT
                        + MediaSizes.original
                        + item.creditsPosterPath
                )
                .into(
                    binding.castAndCrewImageView
                )
        }
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UICredits>() {
    override fun areItemsTheSame(oldItem: UICredits, newItem: UICredits): Boolean {
        return oldItem.creditsMovieId == newItem.creditsMovieId
    }

    override fun areContentsTheSame(oldItem: UICredits, newItem: UICredits): Boolean {
        return oldItem.creditsMovieId == newItem.creditsMovieId &&
                oldItem.creditsName == newItem.creditsName
    }
}

