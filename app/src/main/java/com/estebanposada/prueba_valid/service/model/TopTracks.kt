package com.estebanposada.prueba_valid.service.model

import com.google.gson.annotations.SerializedName

data class TopTracks(
    @SerializedName("tracks") val tracks: Tracks
)