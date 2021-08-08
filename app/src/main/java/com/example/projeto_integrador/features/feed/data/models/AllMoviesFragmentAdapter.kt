package com.example.projeto_integrador.features.feed.data.models

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.projeto_integrador.features.feed.presentation.AllMoviesFragment

class AllMoviesFragmentAdapter(activity: FragmentActivity, itemCount: Int): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemCount

    }

    override fun createFragment(position: Int): Fragment {
        return AllMoviesFragment().getInstance(position)
    }
}