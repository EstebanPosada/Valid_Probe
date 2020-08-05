package com.estebanposada.prueba_valid.service.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.estebanposada.prueba_valid.PAGE_INDEX
import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.model.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class ArtistsRemoteMediator(
    private val database: AppDataBase,
    private val apiKey: String,
    private val query: String,
    private val service: Api

) : RemoteMediator<Int, Artist>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Artist>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }

        }

        try {
            val apiResponse = service.fetchTopArtists(apiKey, page, state.config.pageSize)

            val artists = apiResponse.topArtists.artist
            val endOfPaginationReached = artists.isEmpty()
            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    if (query.isEmpty()) {
                        database.remoteKeysDao().clearRemoteKeys()
                        database.artistDao().clearArtists()
                    }
                }
                val prevKey = if (page == PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                database.artistDao().insertAll(artists)
                val k = database.artistDao().getArtistsKeys().map {
                    RemoteKeys(artistId = it, prevKey = prevKey, nextKey = nextKey) }
                database.remoteKeysDao().insertAll(k)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Artist>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { artist ->
                // Get the remote keys of the last item retrieved
                database.remoteKeysDao().remoteKeysArtistId(artist.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Artist>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { artist ->
                // Get the remote keys of the first items retrieved
                database.remoteKeysDao().remoteKeysArtistId(artist.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Artist>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { artistId ->
                database.remoteKeysDao().remoteKeysArtistId(artistId)
            }
        }
    }
}