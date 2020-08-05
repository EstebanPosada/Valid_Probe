package com.estebanposada.prueba_valid.ui.artist

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ArtistLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ArtistLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ArtistLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ArtistLoadStateViewHolder =
        ArtistLoadStateViewHolder.create(parent, retry)
}