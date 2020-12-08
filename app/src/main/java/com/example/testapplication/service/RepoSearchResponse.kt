package com.example.testapplication.service

import com.example.testapplication.db.entities.RepoEntity
import com.google.gson.annotations.SerializedName

data class RepoSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<RepoEntity> = emptyList()
)