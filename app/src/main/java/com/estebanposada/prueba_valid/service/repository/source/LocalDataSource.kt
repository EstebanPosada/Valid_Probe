package com.estebanposada.prueba_valid.service.repository.source

import com.estebanposada.prueba_valid.service.model.Artist

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveAll(artists: List<Artist>)
    suspend fun getTopArtist(): List<Artist>
    suspend fun findById(id: String): Artist

}