package com.estebanposada.prueba_valid.service.model

import com.google.gson.annotations.SerializedName

data class ArtistObject(
    @SerializedName("topartists") val topArtists: TopArtists
)