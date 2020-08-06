package com.estebanposada.prueba_valid.service.repository

import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.repository.source.LocalDataSource

class RoomDataSource(db: AppDataBase) : LocalDataSource {

    private val artistDao = db.artistDao()

    override suspend fun isEmpty(): Boolean = artistDao.artistCount() <= 0

    override suspend fun saveAll(artists: List<Artist>) {
        artistDao.insertAll(artists)
    }

    override suspend fun findById(id: Long): Artist =
        artistDao.findById(id)

    override suspend fun getAll(): List<Artist> = artistDao.getAll()
}