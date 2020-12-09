package com.example.testapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.db.entities.RepoEntity
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoAdapter() : ListAdapter<RepoEntity, RepoAdapter.RepoViewHolder>(REPO_DIFF_UTIL) {

    companion object {
        private val REPO_DIFF_UTIL = object : DiffUtil.ItemCallback<RepoEntity>() {
            override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repo: RepoEntity) {
            itemView.repo_name.text = repo.fullName
            itemView.repo_description.text = repo.description
            itemView.repo_stars.text = repo.stars.toString()
            itemView.repo_forks.text = repo.forks.toString()

            if (!repo.language.isNullOrEmpty()) {
                val resources = this.itemView.context.resources
                itemView.repo_language.text = resources.getString(R.string.language, repo.language)
                itemView.repo_language.visibility = View.VISIBLE
            } else {
                itemView.repo_language.visibility = View.GONE
            }
        }

        companion object {
            fun create(parent: ViewGroup): RepoViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_repo, parent, false)
                return RepoViewHolder(view)
            }
        }
    }
}