package com.snappymob.kotlincomponents.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.AbsentLiveData
import com.snappymob.kotlincomponents.network.Resource
import com.snappymob.kotlincomponents.repository.RepoRepository
import io.realm.RealmResults
import java.util.*


/**
 * Created by ahmedrizwan on 9/10/17.
 * ViewModel for the Repos
 * TODO: Change/Add/Remove ViewModels in this package!
 */
class RepoViewModel constructor(repository: RepoRepository) : ViewModel() {
    var currentRepoUser: String? = null
    val results: LiveData<Resource<RealmResults<Repo>>>

    private val query: MutableLiveData<String> = MutableLiveData()

    init {
        results = Transformations.switchMap(query, {
            when {
                it == null || it.length == 1 -> AbsentLiveData.create()
                else -> repository.loadRepos(it)
            }
        })
    }

    fun setQuery(originalInput: String?, force:Boolean) {
        if(originalInput==null) return
        val input = originalInput.toLowerCase(Locale.getDefault()).trim { it <= ' ' }
        if (input == query.value && !force) {
            return
        }
        query.value = input
    }

}