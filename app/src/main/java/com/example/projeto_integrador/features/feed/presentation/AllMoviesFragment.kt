package com.example.projeto_integrador.features.feed.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.R
import com.example.projeto_integrador.databinding.FragmentAllMoviesBinding
import com.example.projeto_integrador.databinding.GenreButtonRecyclerViewBinding
import com.example.projeto_integrador.features.feed.data.models.AllMoviesRecyclerViewAdapter
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.models.GenreRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllMoviesFragment: Fragment() {

    companion object {
        const val ARG_POSITION = "position"
    }

    private val binding get() = _binding!!

    private val viewModel: AllMoviesViewModel by viewModel()
    private var _binding: FragmentAllMoviesBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllMoviesBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        requestInitialMovieList()
    }

    private fun setupUI() {
        val movieRecyclerViewAdapter = createMoviesRecyclerViewAdapter()
        val genreRecyclerViewAdapter = createGenreRecyclerViewAdapter()
        setupMovieRecyclerView(movieRecyclerViewAdapter)
        observeMovieViewStateUpdates(movieRecyclerViewAdapter)
        setupGenreRecyclerView(genreRecyclerViewAdapter)
        observeGenreViewStateUpdates(genreRecyclerViewAdapter)
    }


    private fun createMoviesRecyclerViewAdapter(): AllMoviesRecyclerViewAdapter {
        return AllMoviesRecyclerViewAdapter()
    }

    private fun createGenreRecyclerViewAdapter(): GenreRecyclerViewAdapter {
        return GenreRecyclerViewAdapter()
    }

    private fun setupMovieRecyclerView(allMoviesRecyclerViewAdapter: AllMoviesRecyclerViewAdapter) {
        binding.moviesRecyclerView.apply {
            adapter = allMoviesRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addOnScrollListener(createInfiniteScrollListener (layoutManager as LinearLayoutManager))

        }
    }

    private fun setupGenreRecyclerView(genreRecyclerViewAdapter: GenreRecyclerViewAdapter) {
        binding.genreButtonRecyclerView.apply {
            adapter = genreRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addOnScrollListener(createInfiniteScrollListener (layoutManager as LinearLayoutManager))
        }
    }

    private fun requestInitialMovieList() {
        viewModel.onEvent(AllMoviesEvent.RequestInitialMoviesList)
    }

    private fun createInfiniteScrollListener(
        layoutManager: LinearLayoutManager
    ): RecyclerView.OnScrollListener {
        return object: InfiniteScrollListener(
            layoutManager,
            AllMoviesViewModel.UI_PAGE_SIZE
        ) {
            override fun loadMoreItems() {requestMoreMovies()}
            override fun isLoading(): Boolean = viewModel.isLoadingMoreMovies
            override fun isLastPage(): Boolean = viewModel.isLastPage
        }
    }

    private fun observeMovieViewStateUpdates(
        recyclerViewAdapterAll: AllMoviesRecyclerViewAdapter
    ) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateMoviesScreenState(it, recyclerViewAdapterAll)
        }
    }

    private fun observeGenreViewStateUpdates(
        genreRecyclerViewAdapter: GenreRecyclerViewAdapter
    ) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateGenreScreenState(it, genreRecyclerViewAdapter)
        }
    }

    private fun requestMoreMovies() {
        viewModel.onEvent(AllMoviesEvent.RequestMoreMovies)
    }

    private fun updateMoviesScreenState(
        state: AllMoviesViewState,
        allMoviesRecyclerViewAdapter: AllMoviesRecyclerViewAdapter
    ) {
        binding.progressBar.isVisible = state.loading
        allMoviesRecyclerViewAdapter.submitList(state.movies)
        handleNoMoreMovies(state.noMoreMovies)
        handleFailures(state.failure)
    }

    private fun updateGenreScreenState(
        state: AllMoviesViewState,
        genreRecyclerViewAdapter: GenreRecyclerViewAdapter
    ) {
        binding.progressBar.isVisible = state.loading
        genreRecyclerViewAdapter.submitList(state.genre)
        handleNoMoreGenres(state.noMoreGenre)
        handleFailures(state.failure)
    }

    private fun handleNoMoreMovies(noMoreMovies: Boolean) { }

    private fun handleNoMoreGenres(noMoreGenres: Boolean) { }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)

        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        }
        else {
            unhandledFailure.message!! }
        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//
//        adapter = null
//        binding.recyclerView.adapter = null
//        _binding = null
//    }
}