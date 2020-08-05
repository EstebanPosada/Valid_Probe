package com.estebanposada.prueba_valid.service.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.estebanposada.prueba_valid.service.model.Artist

@Dao
interface ArtistDao {

    @Query("SELECT COUNT(id) FROM Artist")
    suspend fun artistCount(): Int

    @Query("SELECT * FROM Artist WHERE id = :id")
    suspend fun findById(id: Long): Artist
    @Query("SELECT * FROM Artist WHERE id = :id")
    fun findByIds(id: Long): Artist

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(artists: List<Artist>)

    @Query("SELECT * FROM Artist WHERE name LIKE :queryString ORDER BY name DESC")
    fun artistByName(queryString: String): PagingSource<Int, Artist>

    @Query("DELETE FROM Artist")
    suspend fun clearArtists()

    @Query("SELECT * FROM Artist")
    fun getAll(): List<Artist>

    @Query("SELECT id FROM Artist")
    suspend fun getArtistsKeys(): List<Long>

}