package com.estebanposada.prueba_valid.di

import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.repository.Api
import com.estebanposada.prueba_valid.service.repository.MainRepositoryImpl
import com.estebanposada.prueba_valid.service.repository.TrackRepositoryImpl
import com.estebanposada.prueba_valid.service.repository.source.LocalDataSource
import com.estebanposada.prueba_valid.service.repository.source.MainRepository
import com.estebanposada.prueba_valid.service.repository.source.TrackRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    @Named("apiKey")
    fun providesApiKey(): String = "829751643419a7128b7ada50de590067"

    @Provides
    @Singleton
    fun providesMainRepository(
        db: AppDataBase,
        localDataSource: LocalDataSource,
        service: Api,
        @Named("apiKey") apiKey: String
    ): MainRepository =
        MainRepositoryImpl(db, localDataSource, service, apiKey)

    @Provides
    @Singleton
    fun provideTrackRepository(
        db: AppDataBase,
        service: Api,
        @Named("apiKey") apiKey: String
    ): TrackRepository = TrackRepositoryImpl(db, service, apiKey)
}