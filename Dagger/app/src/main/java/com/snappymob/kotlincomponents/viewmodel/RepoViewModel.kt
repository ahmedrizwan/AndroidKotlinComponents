package com.snappymob.kotlincomponents.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.Resource
import com.snappymob.kotlincomponents.repository.RepoRepository
import javax.inject.Inject


/**
 * Created by ahmedrizwan on 9/10/17.
 */

class RepoViewModel
@Inject constructor(repository: RepoRepository) : ViewModel() {
    var repo: LiveData<Resource<List<Repo>>>? = null
    val repoRepository = repository

    fun loadRepos(username: String): LiveData<Resource<List<Repo>>>? {
        if (repo == null) {
            repo = repoRepository.loadRepos(username)
        }
        return repo
    }
}