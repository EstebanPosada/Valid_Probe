package com.estebanposada.prueba_valid.service.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.estebanposada.prueba_valid.DEFAULT_PAGE_SIZE
import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.model.Track
import com.estebanposada.prueba_valid.service.repository.source.TrackRepository
import kotlinx.coroutines.flow.Flow

class TrackRepositoryImpl(
    private val db: AppDataBase,
    private val service: Api,
    private val apiKey: String
) : TrackRepository {
    private val trackDao = db.trackDao()
    override fun getData(query: String): Flow<PagingData<Track>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { db.trackDao().findByName(dbQuery) }

        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = TrackRemoteMediator(
                db,
                apiKey,
                query,
                service
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getTrackDetail(id: Long): Track = trackDao.findById(id)
}