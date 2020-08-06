package com.estebanposada.prueba_valid.service.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.estebanposada.prueba_valid.ARTIST_DB_NAME
import com.estebanposada.prueba_valid.service.model.Artist
import com.estebanposada.prueba_valid.service.model.RemoteKeysArtist
import com.estebanposada.prueba_valid.service.model.RemoteKeysTrack
import com.estebanposada.prueba_valid.service.model.Track

@Database(
    entities = [
        Artist::class,
        Track::class,
        RemoteKeysArtist::class,
        RemoteKeysTrack::class], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            ARTIST_DB_NAME
        ).build()
    }

    //abstract fun artistDao(): ArtistDao
    abstract fun artistDao(): ArtistDao
    abstract fun remoteKeysDao(): RemoteKeysArtistDao
    abstract fun trackDao(): TrackDao
    abstract fun remoteKeysTrackDao(): RemoteKeyTrackDao
}
