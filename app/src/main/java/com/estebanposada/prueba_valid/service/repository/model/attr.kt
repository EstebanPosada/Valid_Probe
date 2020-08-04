package com.estebanposada.prueba_valid.service.repository.model

import com.google.gson.annotations.SerializedName

data class attr(
    @SerializedName("country") val country: String,
    @SerializedName("page") val page: Int,
    @SerializedName("perPage") val perPage: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("total") val total: Int
)