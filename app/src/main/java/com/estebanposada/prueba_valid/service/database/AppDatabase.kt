package com.estebanposada.prueba_valid.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.estebanposada.prueba_valid.ARTIST_DB_NAME
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.model.RemoteKeys

@Database(entities = [Artist::class, RemoteKeys::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    companion object {
                fun build(context: Context) = Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            ARTIST_DB_NAME
        ).build()
//        @Volatile
//        private var INSTANCE: AppDataBase? = null
//        fun getInstance(context: Context): AppDataBase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//            }
//
//        private fun buildDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                AppDataBase::class.java,
//                ARTIST_DB_NAME
//            )
//                .build()
    }

    abstract fun artistDao(): ArtistDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
