package com.estebanposada.prueba_valid.service.repository.model

import com.google.gson.annotations.SerializedName

data class TopTracks(
    @SerializedName("tracks") val tracks: Tracks
)