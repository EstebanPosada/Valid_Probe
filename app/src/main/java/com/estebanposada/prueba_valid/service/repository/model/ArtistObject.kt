package com.estebanposada.prueba_valid.service.repository.model

import com.google.gson.annotations.SerializedName

data class ArtistObject(
    @SerializedName("topartists") val topArtists: TopArtists
)