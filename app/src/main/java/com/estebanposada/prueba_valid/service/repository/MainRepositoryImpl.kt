package com.estebanposada.prueba_valid.service.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.repository.source.LocalDataSource
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(
    private val db: AppDataBase,
    private val localDataSource: LocalDataSource,
    private val service: Api,
    private val apiKey: String
) : MainRepository {

    override fun getData(query: String): Flow<PagingData<Artist>> {
        Log.wtf("Fetching data", "Query: $query")
//        return Pager(
//            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
//            pagingSourceFactory = { PagingSource(service, apiKey, query) }
//        ).flow
        // appending '%' so we can allow other characters to be before and after the query string
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { db.artistDao().artistByName(dbQuery) }

        return Pager(
            config = PagingConfig(pageSize = 7, enablePlaceholders = false),
            remoteMediator = ArtistsRemoteMediator(
                db,
                apiKey,
                query,
                service
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getArtistDetail(id: Long): Artist {
        val data = localDataSource.getAll()
        return localDataSource.findById(id)
    }
}