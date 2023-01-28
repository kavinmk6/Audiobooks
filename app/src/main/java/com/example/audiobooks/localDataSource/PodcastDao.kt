package com.example.audiobooks.localDataSource

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.audiobooks.model.PodcastFavourite
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

@Dao
interface PodcastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPodcast(podcastFavourite: PodcastFavourite)

    @Update
    suspend fun updatePodcast(podcastFavourite: PodcastFavourite)

    @Query("UPDATE podcast_table SET isFavoourite=:isFavourite WHERE id = :id")
    fun updatePodcastFavourite(isFavourite:Boolean,id:Int)

    @Delete
    suspend fun deletePodcast(podcastFavourite: PodcastFavourite)

    @Query("DELETE FROM podcast_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM podcast_table")
    suspend fun getAllPodcast(): List<PodcastFavourite>
}