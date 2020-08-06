package com.estebanposada.prueba_valid.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Track(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("listeners") val listeners: Int,
    @SerializedName("mbid") val mbid: String,
    @SerializedName("url") val url: String,
    @SerializedName("streamable") val streamable: Streamable,
    @SerializedName("artist") val artist: Artist,
    @SerializedName("image") val image: List<Image>,
    @SerializedName("@attr") val attr: attr
)