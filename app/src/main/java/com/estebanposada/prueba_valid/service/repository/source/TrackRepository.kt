package com.estebanposada.prueba_valid.service.repository.source

import androidx.paging.PagingData
import com.estebanposada.prueba_valid.service.model.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun getData(query: String): Flow<PagingData<Track>>
    suspend fun getTrackDetail(id: Long): Track
}