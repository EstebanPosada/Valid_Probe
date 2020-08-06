package com.estebanposada.prueba_valid.di

import android.content.Context
import com.estebanposada.prueba_valid.NETWORK_TIME_OUT
import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.repository.Api
import com.estebanposada.prueba_valid.service.repository.RoomDataSource
import com.estebanposada.prueba_valid.service.repository.source.LocalDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun providesService(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    fun providesRetrofit(converterFactory: GsonConverterFactory, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(converterFactory)
            .client(client)
            .build()

    @Provides
    fun providesClient(interceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .connectTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    @Provides
    fun providesConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    fun providesGson(): Gson =
        GsonBuilder().registerTypeAdapter(Date::class.java, DateTypeAdapter()).create()

    @Provides
    fun providesInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun providesLocalDataSource(context: Context): LocalDataSource =
        RoomDataSource(AppDataBase.build(context))

    @Provides
    @Singleton
    fun provideDataBase(context: Context) = AppDataBase.build(context)

}