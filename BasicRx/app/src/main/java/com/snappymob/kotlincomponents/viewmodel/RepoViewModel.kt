package com.snappymob.kotlincomponents.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.util.ArrayMap
import com.snappymob.kotlincomponents.network.AbsentLiveData
import com.snappymob.kotlincomponents.repository.RepoRepository


/**
 * Created by ahmedrizwan on 9/10/17.
 */
class RepoViewModel constructor(val repoRepository: RepoRepository):ViewModel() {

    val map: ArrayMap<String, RepoLiveData> = ArrayMap()

    var query: MutableLiveData<String> = MutableLiveData()

    init {
       Transformations.switchMap(query,
               { search ->
                   if(search==null || search.trim().length==0){
                        AbsentLiveData.create<RepoLiveData>()
                   } else {
                       repoRepository.loadRepos(search)
                   }
               }
       )
    }

    fun loadRepos(username: String): RepoLiveData? {
        query = username
        if (map[query] == null) {
            map[query] = RepoLiveData(repoRepository, owner = username)
        }
        return map[username]
    }

}