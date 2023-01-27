package com.example.audiobooks.localDataSource

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.audiobooks.model.PodcastFavourite

@Database(entities = [PodcastFavourite::class], version = 1, exportSchema = false)
abstract class PodcastDatabase : RoomDatabase() {

    abstract fun getPodcastDao(): PodcastDao

    companion object {

        @Volatile
        private var databaseInstance: PodcastDatabase? = null

        fun getInstanceDatabase(context: Context): PodcastDatabase {

            if (databaseInstance != null) return databaseInstance!!

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PodcastDatabase::class.java,
                    "podcast_database"
                ).fallbackToDestructiveMigration().build()
                databaseInstance = instance
            }
            return databaseInstance!!
        }
    }
}