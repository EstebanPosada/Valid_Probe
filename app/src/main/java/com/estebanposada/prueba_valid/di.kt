package com.estebanposada.prueba_valid

import android.app.Application
import com.estebanposada.prueba_valid.service.database.AppDataBase
import com.estebanposada.prueba_valid.service.repository.Api
import com.estebanposada.prueba_valid.service.repository.MainRepository
import com.estebanposada.prueba_valid.service.repository.MainRepositoryImpl
import com.estebanposada.prueba_valid.ui.main.MainViewModel
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

fun Application.initDI() {
    startKoin {
        androidLogger()
        modules(listOf(appModule, remoteModule))
    }
}

private val appModule = module {
    viewModel { MainViewModel(get()) }

    single {
        MainRepositoryImpl(
            get(),
            "829751643419a7128b7ada50de590067"
        ) as MainRepository
    }
    //single(named("apiKey")) { "829751643419a7128b7ada50de590067" }

    single { AppDataBase.build(get()) }


}

private val remoteModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    single { GsonBuilder().registerTypeAdapter(Date::class.java, DateTypeAdapter()).create() }

    single { GsonConverterFactory.create(get()) }

    single {

        OkHttpClient.Builder()
            .connectTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(get() as HttpLoggingInterceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(get() as GsonConverterFactory)
            .client(get() as OkHttpClient)
            .build()
    }

    single {
        (get() as Retrofit).create(
            Api::class.java
        )
    }
}