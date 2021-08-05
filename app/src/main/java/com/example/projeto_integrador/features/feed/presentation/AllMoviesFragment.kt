package com.example.projeto_integrador.features.feed.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.R
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.models.MoviesAdapter
import com.google.android.material.snackbar.Snackbar

class AllMoviesFragment: Fragment() {

    companion object {
        private const val ITEMS_PER_ROW = 1
    }

    private val binding get() = _binding!!

    private var viewModel: AllMoviesFragmentViewModel by activityViewModels()
    private var _binding: FragmenteAllMoviesBinding? = null

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
        viewModel.isLoggedIn.observe(viewLifecycleOwner, Observer<Boolean> {
            loggedIn ->
            if(loggedIn) {
                requestMoreMovies()
            }
        })
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun createAdapter(): MoviesAdapter {
        return MoviesAdapter()
    }

    private fun setupRecyclerView(moviesAdapter: MoviesAdapter) {
        binding.moviesRecyclerView.apply {
            adapter = allMoviesAdapter
            layoutManager = LinearLayoutManager(context, ITEMS_PER_ROW)
            setHasFixedSize(true)
            addOnScrollListener(createInfiniteSrollListener (layoutManager as LinearLayoutManager))

        }
    }

    private fun createInfiniteSrollListener(
        layoutManager: LinearLayoutManager
    ): RecyclerView.OnScrollListener {
        return object: InfiniteScrollListener(
            layoutManager,
            AllMoviesFragmentViewModel.UI_PAGE_SIZE
        ) {
            override fun loadMoreItems() {requestMoreMovies()}
            override fun isLoading(): Boolean = viewModel.isLoadingMoreMovies
            override fun isLastPage(): Boolean = viewModel.isLastPage
        }
    }

    private fun observeViewStateUpdates(adapter: MoviesAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, adapter)
        }
    }

    private fun requestMoreMovies() {
        viewModel.onEvent(AllMoviesEvent.RequestMoreAnimals)
    }

    private fun updateScreenState(state: AllMoviesViewState, adapter: MoviesAdapter) {
        binding.progressBar.isVisible = state.loading
        adapter.submitList(state.movies)
        handleNoMoreMovies(state.noMoreMovies)
        handleFailures(state.failure)
    }

    private fun handleNoMoreMovies(noMoreMovies: Boolean) {

    }

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

    private fun requestInitialMovieList() {
        viewModel.onEvent(AllMoviesEvent.RequestInitialMovieList)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        adapter = null
        binding.recyclerView.adapter = null
        _binding = null
    }
}