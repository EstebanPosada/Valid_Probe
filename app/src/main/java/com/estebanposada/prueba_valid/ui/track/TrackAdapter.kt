package com.estebanposada.prueba_valid.ui.track

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.estebanposada.prueba_valid.R
import com.estebanposada.prueba_valid.service.model.Track
import kotlinx.android.synthetic.main.track_view_item.view.*

class TrackAdapter : PagingDataAdapter<Track, RecyclerView.ViewHolder>(TRACK_COMPARATOR) {
    var onItemClicked: ((Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.track_view_item, parent, false)
        ) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val trackItem = getItem(position)
        holder.itemView.apply {
            name.text = trackItem!!.name
            artist_name.text = trackItem.artist.name
            length.text =
                String.format(context.getString(R.string.length), trackItem.duration)
            setOnClickListener { onItemClicked?.invoke(trackItem.id) }
        }

    }

    companion object {
        private val TRACK_COMPARATOR = object : DiffUtil.ItemCallback<Track>() {
            override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean =
                oldItem.mbid == newItem.mbid && oldItem.name == newItem.name
        }
    }
}