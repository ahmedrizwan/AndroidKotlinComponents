package com.snappymob.kotlincomponents.viewmodel

import android.arch.lifecycle.ViewModel
import android.util.ArrayMap
import com.snappymob.kotlincomponents.repository.RepoRepository


/**
 * Created by ahmedrizwan on 9/10/17.
 */
class RepoViewModel(repository: RepoRepository):ViewModel() {
    val repoRepository = repository
    val map: ArrayMap<String, RepoLiveData> = ArrayMap()
    var currentRepoUser: String? = null
    fun loadRepos(username: String): RepoLiveData? {
        currentRepoUser = username
        if (map[username] == null) {
            map[username] = RepoLiveData(repoRepository, owner = username)
        }
        return map[username]
    }


}