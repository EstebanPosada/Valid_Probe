package com.estebanposada.prueba_valid.service.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.estebanposada.prueba_valid.PAGE_INDEX
import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.model.RemoteKeysTrack
import com.estebanposada.prueba_valid.service.model.Track
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class TrackRemoteMediator(
    private val database: AppDataBase,
    private val apiKey: String,
    private val query: String,
    private val service: Api
) : RemoteMediator<Int, Track>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Track>): MediatorResult {
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
            val apiResponse = service.fetchTopTracks(apiKey, page, state.config.pageSize)

            val tracks = apiResponse.tracks.track
            val endOfPaginationReached = tracks.isEmpty()
            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH && query.isEmpty()) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.trackDao().clearTracks()
                }
                val prevKey = if (page == PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                database.trackDao().insertAll(tracks)
                val k = database.trackDao().getKeys().map {
                    RemoteKeysTrack(tracktId = it, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysTrackDao().insertAll(k)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Track>): RemoteKeysTrack? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { track ->
                // Get the remote keys of the last item retrieved
                database.remoteKeysTrackDao().remoteKeysTrackId(track.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Track>): RemoteKeysTrack? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { track ->
                // Get the remote keys of the first items retrieved
                database.remoteKeysTrackDao().remoteKeysTrackId(track.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Track>
    ): RemoteKeysTrack? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeysTrackDao().remoteKeysTrackId(repoId)
            }
        }
    }
}