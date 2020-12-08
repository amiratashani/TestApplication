package com.example.testapplication.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoService {

    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String): Response<RepoSearchResponse>

}