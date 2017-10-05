package com.snappymob.kotlincomponents.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.AbsentLiveData
import com.snappymob.kotlincomponents.network.Resource
import com.snappymob.kotlincomponents.repository.RepoRepository
import java.util.*


/**
 * Created by ahmedrizwan on 9/10/17.
 */
class RepoViewModel constructor(repository: RepoRepository) : ViewModel() {
    //    val repoRepository = repository
//    val map: ArrayMap<String, LiveData<Resource<List<Repo>>>> = ArrayMap()
    var currentRepoUser: String? = null

    //live data of list of Repos, called results
    val results: LiveData<Resource<List<Repo>>>
    private val query: MutableLiveData<String> = MutableLiveData()

    init {
        results = Transformations.switchMap(query, {
            when {
                it == null || it.length == 1 -> AbsentLiveData.create()
                else -> repository.loadRepos(it)
            }
        })
    }

    fun setQuery(originalInput: String?) {
        if(originalInput==null) return
        val input = originalInput.toLowerCase(Locale.getDefault()).trim { it <= ' ' }
        if (Objects.equals(input, query.value)) {
            return
        }
        query.value = input
    }

}