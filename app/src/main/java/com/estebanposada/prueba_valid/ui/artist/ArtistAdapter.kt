package com.estebanposada.prueba_valid.ui.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.estebanposada.prueba_valid.R
import com.estebanposada.prueba_valid.service.model.Artist
import kotlinx.android.synthetic.main.artist_view_item.view.*

class ArtistAdapter : PagingDataAdapter<Artist, RecyclerView.ViewHolder>(ARTIST_COMPARATOR) {
    var onItemClicked: ((Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
//        ArtistViewHolder.create(parent)
        object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.artist_view_item, parent, false)
        ) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val artistItem = getItem(position)
        holder.itemView.apply {
            name.text = artistItem!!.name
            listeners.text =
                String.format(context.getString(R.string.listeners), artistItem.listeners)
            setOnClickListener { onItemClicked?.invoke(artistItem.id) }
        }

    }

    companion object {
        private val ARTIST_COMPARATOR = object : DiffUtil.ItemCallback<Artist>() {
            override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean =
                oldItem.mbid == newItem.mbid && oldItem.name == newItem.name
        }
    }
}