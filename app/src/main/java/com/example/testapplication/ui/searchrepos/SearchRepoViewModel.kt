package com.example.testapplication.ui.searchrepos

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.testapplication.db.entities.RepoEntity
import com.example.testapplication.other.Constants.KEY_QUERY
import com.example.testapplication.repository.RepoRepository
import com.example.testapplication.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchRepoViewModel @ViewModelInject constructor(
    private val repository: RepoRepository,
    private val sharedPref: SharedPreferences
) : ViewModel() {

    private val _query = MutableLiveData<String>()

    private val _repos = _query.switchMap { query -> repository.getRepos(query) }
    val repos: LiveData<Resource<List<RepoEntity>>>
        get() = _repos

    fun search(query: String) {
        _query.value = query
        writeQueryToSharedPref(query)
    }


    private fun writeQueryToSharedPref(query: String) =
        sharedPref.edit()
            .putString(KEY_QUERY, query)
            .apply()


    fun readQueryFromSharePref() =
        sharedPref.getString(KEY_QUERY, "")!!

}