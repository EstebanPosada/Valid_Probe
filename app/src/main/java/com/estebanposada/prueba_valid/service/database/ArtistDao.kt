package com.estebanposada.prueba_valid.service.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.estebanposada.prueba_valid.service.model.Artist

@Dao
interface ArtistDao {

    @Query("SELECT * FROM Artist WHERE id = :id")
    suspend fun findById(id: String): Artist

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(artists: List<Artist>)

    @Query("SELECT * FROM Artist WHERE name LIKE :queryString ORDER BY name DESC")
    fun artistByName(queryString: String): PagingSource<Int, Artist>

    @Query("DELETE FROM Artist")
    suspend fun clearArtists()

}