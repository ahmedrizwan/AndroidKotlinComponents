package com.snappymob.kotlincomponents.db

import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.utils.LiveRealmData
import com.snappymob.kotlincomponents.utils.asLiveData
import io.realm.Realm
import io.realm.Sort


class RepoDao(val realm:Realm) {
    fun insertRepos(item: List<Repo>){
        realm.executeTransaction({
            realm.copyToRealmOrUpdate(item)
        })
    }

    fun loadRepositories(owner: String): LiveRealmData<Repo> {
        return realm.where(Repo::class.java)
                .equalTo("owner.login", owner)
                .findAllSorted("stars", Sort.DESCENDING)
                .asLiveData()
    }

}