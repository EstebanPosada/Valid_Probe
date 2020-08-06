package com.estebanposada.prueba_valid.service.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.estebanposada.prueba_valid.service.model.RemoteKeysTrack

@Dao
interface RemoteKeyTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeyTrack: List<RemoteKeysTrack>)

    @Query("SELECT * FROM tracks_keys WHERE tracktId = :trackId")
    suspend fun remoteKeysTrackId(trackId: Long): RemoteKeysTrack?

    @Query("DELETE FROM tracks_keys")
    suspend fun clearRemoteKeys()
}