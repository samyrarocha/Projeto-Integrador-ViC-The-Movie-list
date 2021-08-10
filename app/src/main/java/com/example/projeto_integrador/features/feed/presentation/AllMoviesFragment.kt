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
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.models.AllMoviesRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar

class AllMoviesFragment: Fragment() {

    companion object {
        const val ARG_POSITION = "position"
    }

    private val binding get() = _binding!!

    private val viewModel: AllMoviesViewModel by viewModel
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
        val adapter = createRecyclerViewAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    fun getInstance(position: Int): Fragment {
        val allMoviesFragment = AllMoviesFragment()
        val bundle = Bundle()
        bundle.putInt(ARG_POSITION, position)
        allMoviesFragment.arguments = bundle
        return allMoviesFragment
    }

    private fun createRecyclerViewAdapter(): AllMoviesRecyclerViewAdapter {
        return AllMoviesRecyclerViewAdapter()
    }

    private fun setupRecyclerView(allMoviesRecyclerViewAdapter: AllMoviesRecyclerViewAdapter) {
        binding.moviesRecyclerView.apply {
            adapter = allMoviesRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addOnScrollListener(createInfiniteSrollListener (layoutManager as LinearLayoutManager))

        }
    }

    private fun requestInitialMovieList() {
        viewModel.onEvent(AllMoviesEvent.RequestInitialMoviesList)
    }

    private fun createInfiniteSrollListener(
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

    private fun observeViewStateUpdates(recyclerViewAdapterAll: AllMoviesRecyclerViewAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, recyclerViewAdapterAll)
        }
    }

    private fun requestMoreMovies() {
        viewModel.onEvent(AllMoviesEvent.RequestMoreMovies)
    }

    private fun updateScreenState(state: AllMoviesViewState, adapter: AllMoviesRecyclerViewAdapter) {
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

//    override fun onDestroyView() {
//        super.onDestroyView()
//
//        adapter = null
//        binding.recyclerView.adapter = null
//        _binding = null
//    }
}