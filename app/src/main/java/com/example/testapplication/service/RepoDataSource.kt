package com.example.testapplication.service

import javax.inject.Inject

class RepoDataSource @Inject constructor(
    private val service: RepoService
) : BaseDataSource() {
    suspend fun getRepos(query: String) = getResult { service.searchRepos(query) }
}