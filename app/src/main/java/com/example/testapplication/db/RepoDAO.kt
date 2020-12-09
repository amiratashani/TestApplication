package com.example.testapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapplication.db.entities.RepoEntity

@Dao
interface RepoDAO {

    @Query("SELECT * FROM repoentity")
    suspend fun getAllRepos(): LiveData<List<RepoEntity>>

    @Query("DELETE FROM repoentity")
    suspend fun clearRepos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)


}