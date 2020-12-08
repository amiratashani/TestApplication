package com.example.testapplication.di

import android.content.Context
import androidx.room.Room
import com.example.testapplication.db.RepoDAO
import com.example.testapplication.db.RepoDatabase
import com.example.testapplication.other.Constants.BASE_URL
import com.example.testapplication.other.Constants.REPOSITORIES_DATABASE_NAME
import com.example.testapplication.service.RepoService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepoDatabase(@ApplicationContext app: Context): RepoDatabase =
        Room.databaseBuilder(
            app,
            RepoDatabase::class.java,
            REPOSITORIES_DATABASE_NAME
        )
            .build()

    @Provides
    @Singleton
    fun provideRepoDao(db: RepoDatabase): RepoDAO = db.getRepoDao()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideREpoService(retrofit: Retrofit): RepoService =
        retrofit.create(RepoService::class.java)


}