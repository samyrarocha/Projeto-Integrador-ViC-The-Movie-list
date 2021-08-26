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
import com.example.projeto_integrador.features.feed.presentation.ErrorDialogFragment
import com.example.projeto_integrador.features.moviedetails.data.models.CreditsRecyclerViewAdapter
import com.example.projeto_integrador.features.moviedetails.data.models.DetailsGenreRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.time.ExperimentalTime

class MovieDetailsFragment: Fragment() {


    private val binding get() = _binding!!

    private val viewModel: MovieDetailsViewModel by viewModel()
    private var _binding: FragmentMovieDetailsBinding? = null
    private var movieId: Long? = null

    private val detailsGenreRecyclerViewAdapter: DetailsGenreRecyclerViewAdapter by lazy {
        DetailsGenreRecyclerViewAdapter()
    }

    private val creditsRecyclerViewAdapter: CreditsRecyclerViewAdapter by lazy {
        CreditsRecyclerViewAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root

    }

    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieId = arguments?.getLong("movie_id")
        movieId?.let {
            viewModel.getMovieDetails(it)
        }

        observeViewStateUpdates()
        setupUI()

    }

    @ExperimentalTime
    private fun setupUI() {
        setupCreditsRecyclerView()
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

    private fun setupCreditsRecyclerView() {
        binding.castAndCrewRecyclerView.adapter= creditsRecyclerViewAdapter
        binding.castAndCrewRecyclerView.layoutManager= LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    @ExperimentalTime
    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.updateScreenState()
        }
    }

    @ExperimentalTime
    private fun MovieDetailsViewState.updateScreenState() {
        detailsGenreRecyclerViewAdapter.submitList(movieDetails?.detailsGenreName)
        creditsRecyclerViewAdapter.submitList(movieDetails?.credits)
        binding.movieDetailsProgressBar.isVisible = loading
        binding.detailsMovieTitleTextView.text = movieDetails?.detailsMovieTitle ?: ""
        binding.detailsOverviewTextView.text = movieDetails?.detailsOverview ?: ""
        binding.detailsYearTextView.text = movieDetails?.detailsReleasedYear ?: ""
        binding.detailsRatingTextView.text = movieDetails?.detailsVoteAverage
        binding.detailsRuntimeTextView.text = movieDetails?.detailsRuntime
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

    @ExperimentalTime
    private fun showErrorDialog(){
        activity?.supportFragmentManager?.let {
            ErrorDialogFragment().apply {
                listener = object: ErrorDialogFragment.Listener {
                    override fun onDialogButtonClicked() {
                        movieId?.let { id ->
                            viewModel.getMovieDetails(id)
                        }
                    }
                }
            }.show(it, "MovieDetailsFragment")
        }
    }


    @ExperimentalTime
    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)

        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        } else {
            unhandledFailure.message!!
        }
        if (snackbarMessage.isNotEmpty()) {
            showErrorDialog()
        }
    }
}