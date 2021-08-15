package com.example.projeto_integrador.features.feed.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_integrador.R
import com.example.projeto_integrador.databinding.FragmentAllMoviesBinding
import com.example.projeto_integrador.features.feed.data.models.AllMoviesRecyclerViewAdapter
import com.example.projeto_integrador.features.feed.data.models.Event
import com.example.projeto_integrador.features.feed.data.models.GenreRecyclerViewAdapter
import com.example.projeto_integrador.features.feed.data.ui.UIGenre
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllMoviesFragment: Fragment() {

    companion object {
        const val ARG_POSITION = "position"
    }

    private val binding get() = _binding!!

    private val viewModel: AllMoviesViewModel by viewModel()
    private var _binding: FragmentAllMoviesBinding? = null
    private val allMoviesRecyclerViewAdapter: AllMoviesRecyclerViewAdapter
    by lazy {
        AllMoviesRecyclerViewAdapter(::navigateToDetails)
    }
    private val genreRecyclerViewAdapter: GenreRecyclerViewAdapter by lazy {
        GenreRecyclerViewAdapter(){
            updateSelectedGenre(it)
        }
    }
    private fun navigateToDetails(movieId: Long){
        val bundle = bundleOf("movie_id" to movieId)
        view?.findNavController()?.navigate(
            R.id.navigateToMovieDetails, bundle)

    }

    private fun updateSelectedGenre(selectedGenre: UIGenre){
        viewModel.onMoviesEvent(
            AllMoviesEvent.UpdateGenre(selectedGenre)
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllMoviesBinding.inflate(
            inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        requestInitialMovieList()
        requestGenreList()
    }

    private fun setupUI() {
        setupMovieRecyclerView()
        observeViewStateUpdates()
        setupGenreRecyclerView()
        observeGenreSelection()
    }

    private fun setupMovieRecyclerView() {
        binding.moviesRecyclerView.apply {
            adapter = allMoviesRecyclerViewAdapter
            PagerSnapHelper().attachToRecyclerView(this)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false)
            setHasFixedSize(true)
            addOnScrollListener(createInfiniteScrollListener(
                layoutManager as LinearLayoutManager))

        }
    }

    private fun setupGenreRecyclerView() {
        binding.genreRecyclerView.adapter= genreRecyclerViewAdapter
        binding.genreRecyclerView.layoutManager= LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun requestInitialMovieList() {
        viewModel.onMoviesEvent(AllMoviesEvent.RequestInitialMoviesList)
    }

    private fun requestGenreList() {
        viewModel.onGenreEvent(GenreEvent.RequestGenreList)
    }

    private fun createInfiniteScrollListener(
        layoutManager: LinearLayoutManager
    ): RecyclerView.OnScrollListener {
        return object : InfiniteScrollListener(
            layoutManager,
            AllMoviesViewModel.UI_PAGE_SIZE
        ) {
            override fun loadMoreItems() {
                requestMoreMovies()
            }

            override fun isLoading(): Boolean = viewModel.isLoadingMoreMovies
            override fun isLastPage(): Boolean = viewModel.isLastPage
        }
    }

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.updateScreenState()
        }
    }

    private fun observeGenreSelection() {
        viewModel.selectedGenre.observe(
            viewLifecycleOwner, Observer { newGenre ->
            val previousSelectedGenre = genreRecyclerViewAdapter.selectedGenre
            genreRecyclerViewAdapter.selectedGenre = newGenre
            previousSelectedGenre?.let {
                viewModel.state.value?.genre?.indexOf(it)?.let {
                    genreRecyclerViewAdapter.notifyItemChanged(it)
                }
            }

            newGenre?.let {
                viewModel.state.value?.genre?.indexOf(it)?.let {
                    genreRecyclerViewAdapter.notifyItemChanged(it)
                }
            }
        })
    }

    private fun requestMoreMovies() {
        viewModel.onMoviesEvent(AllMoviesEvent.RequestMoreMovies)
    }

    private fun AllMoviesViewState.updateScreenState() {
        binding.progressBar.isVisible = loading
        allMoviesRecyclerViewAdapter.submitList(movies)
        genreRecyclerViewAdapter.submitList(genre)
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



//    override fun onDestroyView() {
//        super.onDestroyView()
//
//        adapter = null
//        binding.recyclerView.adapter = null
//        _binding = null
//    }
}