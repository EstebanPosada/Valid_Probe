package com.estebanposada.prueba_valid.service.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.estebanposada.prueba_valid.service.model.Track

@Dao
interface TrackDao {

    @Query("SELECT COUNT(id) FROM  Track")
    suspend fun trackCount(): Int

    @Query("SELECT * FROM Track WHERE id = :id")
    suspend fun findById(id: Long): Track

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(tracks: List<Track>)

    @Query("SELECT * FROM Track WHERE name LIKE :queryString ORDER BY name DESC")
    fun findByName(queryString: String): PagingSource<Int, Track>

    @Query("DELETE FROM Artist")
    suspend fun clearTracks()

    @Query("SELECT id FROM Track")
    suspend fun getKeys(): List<Long>
}