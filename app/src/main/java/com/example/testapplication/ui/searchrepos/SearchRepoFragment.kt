package com.example.testapplication.ui.searchrepos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.testapplication.R
import com.example.testapplication.adapter.RepoAdapter
import com.example.testapplication.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_repo.*
import timber.log.Timber


@AndroidEntryPoint
class SearchRepoFragment : Fragment() {

    private val viewModel: SearchRepoViewModel by viewModels()

    private lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        setupObservers()
        viewModel.search("Android")
    }

    private fun setupRecycleView() {
        adapter = RepoAdapter()
        list.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.repos.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    Timber.e("SUCCESS")
                    Timber.e("${result.data}")
                    progress_bar.visibility = View.GONE
                    if (!result.data.isNullOrEmpty()) adapter.submitList(result.data)
                }
                Resource.Status.ERROR -> {
                }
                Resource.Status.LOADING -> {
                    Timber.e("LOADING")
                    progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchRepoFragment().apply {}
    }
}