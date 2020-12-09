package com.example.testapplication.repository

import com.example.testapplication.db.RepoDAO
import com.example.testapplication.service.RepoDataSource
import com.example.testapplication.utils.getDataAndCaching
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val repoDataSource: RepoDataSource,
    private val repoDAO: RepoDAO
) {
    fun getRepos(query: String) = getDataAndCaching(
        databaseQuery = {repoDAO.getAllRepos()},
        networkCall = { repoDataSource.getRepos(query) },
        saveCallResult = { repoSearchResponse -> repoDAO.insertAll(repoSearchResponse.items) }
    )

    suspend fun clearRepos(){
        repoDAO.clearRepos()
    }
}
