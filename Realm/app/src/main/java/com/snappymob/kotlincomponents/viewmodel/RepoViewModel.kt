package com.snappymob.kotlincomponents.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.ArrayMap
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.Resource
import com.snappymob.kotlincomponents.repository.RepoRepository
import io.realm.RealmResults


/**
 * Created by ahmedrizwan on 9/10/17.
 */
class RepoViewModel(repository: RepoRepository):ViewModel() {
    val repoRepository = repository
    val map: ArrayMap<String, LiveData<Resource<RealmResults<Repo>>>> = ArrayMap()
    var currentRepoUser: String? = null
    fun loadRepos(username: String): LiveData<Resource<RealmResults<Repo>>>? {
        currentRepoUser = username
        if (map[username] == null) {
            map[username] = repoRepository.loadRepos(username)
        }
        return map[username]
    }
}