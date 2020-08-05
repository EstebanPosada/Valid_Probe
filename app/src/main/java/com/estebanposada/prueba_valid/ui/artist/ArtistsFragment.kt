package com.estebanposada.prueba_valid.ui.artist

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.estebanposada.prueba_valid.App
import com.estebanposada.prueba_valid.EMPTY
import com.estebanposada.prueba_valid.LAST_SEARCH_QUERY
import com.estebanposada.prueba_valid.databinding.FragmentArtistsBinding
import com.estebanposada.prueba_valid.ui.main.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtistsFragment : Fragment() {

    private var _binding: FragmentArtistsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: MainViewModel
    private val adapter = ArtistAdapter()

    private var searchJob: Job? = null

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchArtists(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        (requireContext().applicationContext as App).appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvArtists.addItemDecoration(decoration)

        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: EMPTY
        search(query)
        initSearch(query)

        adapter.onItemClicked = {
            view?.findNavController().navigate(
                ArtistsFragmentDirections.actionArtistsFragmentToArtistDetailFragment(
                    it
                )
            )
        }
        binding.retryButton.setOnClickListener { adapter.retry() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.search.text.trim().toString())
    }

    private fun initAdapter() {
        binding.rvArtists.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ArtistLoadStateAdapter { adapter.retry() },
            footer = ArtistLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.rvArtists.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initSearch(query: String) {
        binding.search.setText(query)

        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.search.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
        binding.search.text!!.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}