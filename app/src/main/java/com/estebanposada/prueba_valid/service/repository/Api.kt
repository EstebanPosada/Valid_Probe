package com.estebanposada.prueba_valid.service.repository

import com.estebanposada.prueba_valid.service.repository.model.ArtistObject
import com.estebanposada.prueba_valid.service.repository.model.TopTracks
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("?method=geo.gettopartists&country=spain&format=json")
    suspend fun fetchTopArtists(
        @Query("api_key") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ArtistObject

    @GET("?method=geo.gettoptracks&country=spain&format=json")
    suspend fun fetchTopTracks(
        @Query("api_key") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): TopTracks
}