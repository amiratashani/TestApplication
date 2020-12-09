package com.example.testapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testapplication.db.entities.RepoEntity

@Database(entities = [RepoEntity::class], version = 1,exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun getRepoDao(): RepoDAO
}