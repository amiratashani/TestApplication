package com.example.testapplication.di

import android.content.Context
import androidx.room.Room
import com.example.testapplication.db.RepoDatabase
import com.example.testapplication.other.Constants.REPOSITORIES_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRunningDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        RepoDatabase::class.java,
        REPOSITORIES_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideRunDao(db: RepoDatabase) = db.getRepoDao()
}