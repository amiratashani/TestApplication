package com.example.testapplication.ui.searchrepos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.testapplication.db.entities.RepoEntity
import com.example.testapplication.repository.RepoRepository
import com.example.testapplication.utils.Resource

class SearchRepoViewModel @ViewModelInject constructor(
    private val repository: RepoRepository
) : ViewModel() {

    private val _query = MutableLiveData<String>()

    private val _repos = _query.switchMap { query -> repository.getRepos(query) }
    val repos: LiveData<Resource<List<RepoEntity>>>
        get() = _repos

    fun search(query: String) {
        _query.value = query
    }
}