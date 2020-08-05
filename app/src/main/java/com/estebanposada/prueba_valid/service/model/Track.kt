package com.estebanposada.prueba_valid.service.model

import com.google.gson.annotations.SerializedName

data class Track(
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