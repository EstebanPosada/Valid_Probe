package com.estebanposada.prueba_valid.service.model

import com.google.gson.annotations.SerializedName

data class TopArtists(
    @SerializedName("artist") val artist: List<Artists>,
    @SerializedName("@attr") val attr: attr
)