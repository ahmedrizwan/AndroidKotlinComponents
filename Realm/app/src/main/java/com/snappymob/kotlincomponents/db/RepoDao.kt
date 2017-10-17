package com.snappymob.kotlincomponents.db

import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.utils.LiveRealmData
import com.snappymob.kotlincomponents.utils.asLiveData
import io.realm.Realm

/**
 * Created by ahmedrizwan on 9/9/17.
 * Realm Dao for Repo
 * TODO: Change the dao based on what you want
 */
class RepoDao(val realm:Realm) {
    fun insertRepos(items: List<Repo>){
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(items)
        realm.commitTransaction()
    }

    fun loadRepositories(owner: String): LiveRealmData<Repo> {
        return realm.where(Repo::class.java)
                .findAll()
//                .equalTo("owner.login", owner)
//                .findAllSorted("stars", Sort.DESCENDING)
                .asLiveData()
    }
}