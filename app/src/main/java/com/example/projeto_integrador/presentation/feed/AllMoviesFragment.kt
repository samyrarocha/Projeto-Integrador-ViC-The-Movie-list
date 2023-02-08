package com.example.projeto_integrador.presentation.feed


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
import com.example.projeto_integrador.presentation.feed.models.Event
import com.example.projeto_integrador.presentation.feed.recyclerview.AllMoviesRecyclerViewAdapter
import com.example.projeto_integrador.presentation.feed.recyclerview.GenreRecyclerViewAdapter
import com.example.projeto_integrador.presentation.models.UIGenre
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val EMPTY = ""

class AllMoviesFragment: Fragment() {


    private val binding get() = _binding!!
    private val viewModel: AllMoviesViewModel by viewModel()
    private var _binding: FragmentAllMoviesBinding? = null
    private var selectedTab: Int = 0
    private val allMoviesRecyclerViewAdapter: AllMoviesRecyclerViewAdapter
    by lazy {
        AllMoviesRecyclerViewAdapter(::navigateToDetails) {
            viewModel.onMoviesEvent(
                AllMoviesEvent.UpdateFavoriteMovie(it)
            )
        }
    }
    private val genreRecyclerViewAdapter: GenreRecyclerViewAdapter by lazy {
        GenreRecyclerViewAdapter{
            updateSelectedGenre(it)
        }
    }
    private fun navigateToDetails(movieId: Long){
        val bundle = bundleOf("movie_id" to movieId)
        view?.findNavController()?.navigate(
            R.id.navigateToMovieDetails, bundle)
    }

    private fun navigateToMoviesFeed(){
        val bundle = bundleOf()
        view?.findNavController()?.navigate(
            R.id.navigateToMovieFeed, bundle)
    }

    private fun updateSelectedGenre(selectedGenre: UIGenre){
        viewModel.onMoviesEvent(
            AllMoviesEvent.UpdateGenre(selectedGenre)
        )
    }

    private fun getFavoriteMovies(){
        viewModel.onMoviesEvent(
            AllMoviesEvent.GetFavoriteMovies
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllMoviesBinding.inflate(
            inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeGenreSelection()
        observeViewStateUpdates()
        setupSearchViewListener()
        requestGenreList()

        binding.moviesTabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                handleTabSelection(tab?.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                handleTabSelection(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                return
            }
        })

        binding.searchEditText.setOnSearchClickListener{
            binding.moviesTabLayout.visibility = View.INVISIBLE
            binding.searchModeTabIndicator.isVisible = true
            binding.searchTabLayout.isVisible = true
            binding.backButton.isVisible = true
        }

        binding.backButton.setOnClickListener{
            binding.moviesTabLayout.visibility = View.VISIBLE
            binding.searchModeTabIndicator.isVisible = false
            binding.searchTabLayout.isVisible = false
            binding.backButton.isVisible = false
            binding.searchEditText.clearFocus()
            requestGenreList()
        }

    }

    private fun handleTabSelection(position: Int?) {
        when (position) {
            0 -> {
                selectedTab = 0
                viewModel.onMoviesEvent(AllMoviesEvent.RequestInitialMoviesList)
            }
            1 -> {
                selectedTab = 1
                getFavoriteMovies()
            }
        }
    }

    private fun setupUI() {
        setupMovieRecyclerView()
        setupGenreRecyclerView()
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

    private fun setupSearchViewListener() {
        binding.searchEditText.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.onMoviesEvent(AllMoviesEvent.PrepareForSearch)
                    viewModel.onMoviesEvent(AllMoviesEvent.QueryInput(query.orEmpty()))
                    binding.searchEditText.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.onMoviesEvent(AllMoviesEvent.PrepareForSearch)
                    viewModel.onMoviesEvent(AllMoviesEvent.QueryInput(newText.orEmpty()))
                    return true
                }
            }
        )
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
            previousSelectedGenre?.let { genre ->
                viewModel.state.value?.genre?.indexOf(genre)?.let {
                    genreRecyclerViewAdapter.notifyItemChanged(it)
                }
            }

            newGenre?.let { genre ->
                viewModel.state.value?.genre?.indexOf(genre)?.let {
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
        when (selectedTab) {
            0 -> allMoviesRecyclerViewAdapter.submitList(movies)
            1 -> allMoviesRecyclerViewAdapter.submitList(favoriteMovie)
        }
        genreRecyclerViewAdapter.submitList(genre)
        handleFailures(failure)
    }

    private fun showErrorDialog(){
        activity?.supportFragmentManager?.let {
            ErrorDialogFragment().apply {
                listener = object: ErrorDialogFragment.Listener {
                    override fun onDialogButtonClicked() {
                        requestGenreList()
                        viewModel.onMoviesEvent(AllMoviesEvent.RequestInitialMoviesList)
                    }
                }
            }.show(it, "ErrorDialogFragment")
        }
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)

        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        } else {
            unhandledFailure.message ?: EMPTY
        }
        if (snackbarMessage.isNotEmpty()) {
            showErrorDialog()
        }
    }
}