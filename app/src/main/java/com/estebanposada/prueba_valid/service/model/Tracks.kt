package com.estebanposada.prueba_valid.service.model

import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("track") val track: List<Track>,
    @SerializedName("@attr") val attr: attr
)