package com.estebanposada.prueba_valid.service.repository.model

import com.google.gson.annotations.SerializedName

data class TopArtists(
    @SerializedName("artist") val artist: List<Artist>,
    @SerializedName("@attr") val attr: attr
)