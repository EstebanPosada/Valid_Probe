package com.estebanposada.prueba_valid.service.repository

import com.estebanposada.prueba_valid.service.repository.model.ArtistResult
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getData(query: String): Flow<ArtistResult>
    suspend fun requestMore(query: String)
}