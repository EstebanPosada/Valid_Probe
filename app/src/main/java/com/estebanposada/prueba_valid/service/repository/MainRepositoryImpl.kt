package com.estebanposada.prueba_valid.service.repository

private class MainRepositoryImpl (
    private val remoteDataSource: DataSource,
    private val localDataSource: DataSource
)