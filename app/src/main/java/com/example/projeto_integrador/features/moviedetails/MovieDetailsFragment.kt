package com.example.projeto_integrador.features.moviedetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto_integrador.R
import com.example.projeto_integrador.common.data.api.models.ApiConstants
import com.example.projeto_integrador.common.domain.model.movies.MediaSizes
import com.example.projeto_integrador.databinding.FragmentMovieDetailsBinding
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.moviedetails.data.DetailsGenreRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment: Fragment() {


    private val binding get() = _binding!!

    private val viewModel: MovieDetailsViewModel by viewModel()
    private var _binding: FragmentMovieDetailsBinding? = null

    private val detailsGenreRecyclerViewAdapter: DetailsGenreRecyclerViewAdapter by lazy {
        DetailsGenreRecyclerViewAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getLong("movie_id")
        movieId?.let {
            viewModel.getMovieDetails(it)
        }

        observeViewStateUpdates()
        setupUI()

    }

    private fun setupUI() {
        setupDetailsGenreRecyclerView()
        observeViewStateUpdates()
    }

    private fun setupDetailsGenreRecyclerView() {
        binding.detailsGenre.adapter= detailsGenreRecyclerViewAdapter
        binding.detailsGenre.layoutManager= LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.updateScreenState()
        }
    }

    private fun MovieDetailsViewState.updateScreenState() {
        detailsGenreRecyclerViewAdapter.submitList(genre)
        binding.movieDetailsProgressBar.isVisible = loading
        binding.detailsMovieTitleTextView.text = movieDetails?.detailsTitle ?: ""
        binding.detailsOverviewTextView.text = movieDetails?.overview ?: ""
        binding.detailsYearTextView.text = movieDetails?.releaseDate ?: ""
        val popularity = movieDetails?.detailsVoteAverage ?: 0
        binding.detailsRatingTextView.text = "${popularity.toInt()*10}%"
        binding.detailsRuntimeTextView.text = movieDetails?.detailsRuntime.toString() ?: ""
        Picasso.get()
            .load(
                ApiConstants.BASE_IMAGE_ENDPOINT
                        + MediaSizes.original
                        + movieDetails?.detailsPosterPath
            )
            .into(
                binding.detailsPosterImageView
            )
        handleFailures(failure)
    }


    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)

        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        } else {
            unhandledFailure.message!!
        }
        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }
}