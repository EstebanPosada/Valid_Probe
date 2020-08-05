package com.estebanposada.prueba_valid.di

import android.content.Context
import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.repository.Api
import com.estebanposada.prueba_valid.service.repository.MainRepository
import com.estebanposada.prueba_valid.service.repository.MainRepositoryImpl
import com.estebanposada.prueba_valid.service.repository.RoomDataSource
import com.estebanposada.prueba_valid.service.repository.source.LocalDataSource
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
        context: Context,
        localDataSource: LocalDataSource,
        service: Api,
        @Named("apiKey") apiKey: String
    ): MainRepository =
        MainRepositoryImpl(AppDataBase.build(context), localDataSource, service, apiKey)

    @Provides
    @Singleton
    fun providesLocalDataSource(context: Context): LocalDataSource =
        RoomDataSource(AppDataBase.build(context))
}