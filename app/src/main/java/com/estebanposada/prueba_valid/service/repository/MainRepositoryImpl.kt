package com.estebanposada.prueba_valid.service.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.estebanposada.prueba_valid.DEFAULT_PAGE_SIZE
import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.model.Artists
import com.estebanposada.prueba_valid.service.repository.source.LocalDataSource
import com.estebanposada.prueba_valid.service.repository.source.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MainRepositoryImpl(
    private val db: AppDataBase,
    private val localDataSource: LocalDataSource,
    private val service: Api,
    private val apiKey: String
) : MainRepository {

    override fun getData(query: String): Flow<PagingData<Artist>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { db.artistDao().artistByName(dbQuery) }

        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = ArtistsRemoteMediator(
                db,
                apiKey,
                query,
                service
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getArtistDetail(id: Long): Artist = localDataSource.findById(id)
}