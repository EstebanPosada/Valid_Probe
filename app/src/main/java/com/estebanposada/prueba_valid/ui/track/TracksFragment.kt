package com.estebanposada.prueba_valid.ui.track

import android.content.Intent
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
import com.estebanposada.prueba_valid.LAST_SEARCH_QUERY_TRACKS
import com.estebanposada.prueba_valid.databinding.FragmentTracksBinding
import com.estebanposada.prueba_valid.ui.artist.ArtistLoadStateAdapter
import com.estebanposada.prueba_valid.ui.main.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


class TracksFragment : Fragment() {

    private var _binding: FragmentTracksBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: TrackViewModel
    private val adapter = TrackAdapter()

    private var searchJob: Job? = null

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchTracks(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTracksBinding.inflate(inflater, container, false)
        (requireContext().applicationContext as App).appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvTracks.addItemDecoration(decoration)

        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY_TRACKS) ?: EMPTY
        search(query)
        initSearch(query)

        adapter.onItemClicked = {
            view?.findNavController()
                .navigate(TracksFragmentDirections.actionTracksFragmentToTrackDetailFragment(it))
        }
        binding.retryButton.setOnClickListener { adapter.retry() }
        binding.goArtist.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    MainActivity::class.java
                )
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY_TRACKS, binding.searchTrack.text.trim().toString())
    }

    private fun initAdapter() {
        binding.rvTracks.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ArtistLoadStateAdapter { adapter.retry() },
            footer = ArtistLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            binding.rvTracks.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

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
        binding.searchTrack.setText(query)

        binding.searchTrack.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchTrack.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvTracks.scrollToPosition(0) }
        }
    }

    private fun updateRepoListFromInput() {
        binding.searchTrack.text!!.trim().let {
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