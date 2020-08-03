package com.estebanposada.prueba_valid.service.repository.model

import com.google.gson.annotations.SerializedName

data class base(
    @SerializedName("tracks") val tracks: Tracks
)

data class Tracks(
    @SerializedName("track") val track: List<Track>,
    @SerializedName("@attr") val attr: attr
)

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

data class Streamable(
    @SerializedName("#text") val text: Int,
    @SerializedName("fulltrack") val fulltrack: Int
)

data class Image(
    @SerializedName("#text") val text: String,
    @SerializedName("size") val size: String
)

data class Artist(
    @SerializedName("name") val name: String,
    @SerializedName("mbid") val mbid: String,
    @SerializedName("url") val url: String
)

data class attr(
    @SerializedName("country") val country: String,
    @SerializedName("page") val page: Int,
    @SerializedName("perPage") val perPage: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("total") val total: Int
)