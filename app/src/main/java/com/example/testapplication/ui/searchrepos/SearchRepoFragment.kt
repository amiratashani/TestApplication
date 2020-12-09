package com.example.testapplication.ui.searchrepos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testapplication.R
import com.example.testapplication.adapter.RepoAdapter
import com.example.testapplication.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_repo.*
import timber.log.Timber


@AndroidEntryPoint
class SearchRepoFragment : Fragment(R.layout.fragment_search_repo) {

    private val viewModel: SearchRepoViewModel by viewModels()

    private lateinit var adapter: RepoAdapter
    private lateinit var query: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = viewModel.readQueryFromSharePref()
        Toast.makeText(requireContext(), "ViewModelsCreate", Toast.LENGTH_SHORT).show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        setupRecycleView()
        setupObservers()
        setupEditText()
        search()
    }

    private fun initUi() {
        search_repo.setText(query)
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

                    if (result.data!!.isEmpty())
                        emptyList.visibility = View.VISIBLE
                    else {
                        emptyList.visibility = View.INVISIBLE
                    }

                    progress_bar.visibility = View.INVISIBLE
                    list.visibility = View.VISIBLE
                    adapter.submitList(result.data)
                }
                Resource.Status.ERROR -> {
                    Timber.e("ERROR")
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.bad_network),
                        Toast.LENGTH_LONG
                    ).show()
                }
                Resource.Status.LOADING -> {
                    Timber.e("LOADING")
                    progress_bar.visibility = View.VISIBLE
                    list.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setupEditText() {
        search_repo.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (query.trim() != search_repo.text.toString().trim()) {
                    query = search_repo.text.toString()
                    search()
                }
                hideKeyboardFrom(requireContext(), root)
                true
            } else {
                false
            }
        }
    }

    private fun search() {
        if (query.trim().isNotEmpty()) {
            viewModel.search(query)
        }
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}