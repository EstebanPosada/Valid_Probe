package com.estebanposada.prueba_valid.ui.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.estebanposada.prueba_valid.R
import com.estebanposada.prueba_valid.databinding.ReloadStateFooterViewItemBinding

class ArtistLoadStateViewHolder(
    private val binding: ReloadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ArtistLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.reload_state_footer_view_item, parent, false)
            val binding = ReloadStateFooterViewItemBinding.bind(view)
            return ArtistLoadStateViewHolder(binding, retry)
        }
    }

}