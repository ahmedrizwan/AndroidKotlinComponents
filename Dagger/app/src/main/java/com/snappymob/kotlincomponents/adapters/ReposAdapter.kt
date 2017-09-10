package com.snappymob.kotlincomponents.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.snappymob.kotlincomponents.R
import com.snappymob.kotlincomponents.model.Repo
import kotlinx.android.synthetic.main.repo_item.view.*

/**
 * Created by ahmedrizwan on 9/10/17.
 */
class ReposAdapter (context: Context, var repos: ArrayList<Repo>) : RecyclerView.Adapter<ReposAdapter.RepoItemViewHolder>() {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        return repos.size
    }

    fun updateDataSet(data: ArrayList<Repo>){
        repos = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): RepoItemViewHolder {
        return RepoItemViewHolder(layoutInflater.inflate(R.layout.repo_item, p0, false))
    }

    override fun onBindViewHolder(p0: RepoItemViewHolder, p1: Int) {
        p0.bind(repos[p1])
    }

    class RepoItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(repo: Repo) = with(itemView) {
            repoName.text = repo.name
        }
    }

}