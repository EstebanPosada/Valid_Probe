package com.estebanposada.prueba_valid.ui.artist

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estebanposada.prueba_valid.databinding.FragmentArtistsBinding
import com.estebanposada.prueba_valid.service.repository.model.ArtistResult
import com.estebanposada.prueba_valid.ui.main.ArtistAdapter
import com.estebanposada.prueba_valid.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArtistsFragment : Fragment() {

    private var _binding: FragmentArtistsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()
    private val adapter = ArtistAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvArtists.addItemDecoration(decoration)
        setupScrollListener()
        initAdapter()
        if (viewModel.result.value == null) {
            viewModel.searchRepo("")
        }
        initSearch("")

        adapter.onItemClicked = {
            view?.findNavController().navigate(
                ArtistsFragmentDirections.actionArtistsFragmentToArtistDetailFragment(
                    it
                )
            )
        }
    }

    private fun initAdapter() {
        binding.rvArtists.adapter = adapter
        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ArtistResult.Success -> {
//                    showEmptyList(result.data.isEmpty())
                    adapter.submitList(result.data)
                }
                is ArtistResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Wooops $result.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
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
                binding.rvArtists.scrollToPosition(0)
                viewModel.searchRepo(it.toString())
            }
        }
    }

    private fun setupScrollListener() {
        val layoutManager = binding.rvArtists.layoutManager as LinearLayoutManager
        binding.rvArtists.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}