package com.snappymob.kotlincomponents.repository

import android.arch.lifecycle.LiveData
import com.snappymob.kotlincomponents.db.RepoDao
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.ApiResponse
import com.snappymob.kotlincomponents.network.AppThreadExecutors
import com.snappymob.kotlincomponents.network.NetworkBoundResource
import com.snappymob.kotlincomponents.network.Resource
import com.snappymob.kotlincomponents.retrofit.WebService
import com.snappymob.kotlincomponents.utils.LiveRealmData
import com.snappymob.kotlincomponents.utils.RateLimiter
import io.realm.RealmResults
import java.util.concurrent.TimeUnit

/**
 * Created by ahmedrizwan on 9/10/17.
 * Repository class - uses NetworkBoundResource to load data from API
 * TODO: Change/Add/Remove Repository files in this package
 */
class RepoRepository(val repoDao: RepoDao, val webService: WebService, val appThreadExecutors: AppThreadExecutors) {
    val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(owner: String): LiveData<Resource<RealmResults<Repo>>> {
        return object : NetworkBoundResource<Repo, List<Repo>>(appThreadExecutors) {
            override fun saveCallResult(item: List<Repo>) {
                repoDao.insertRepos(ArrayList(item))
            }

            override fun shouldFetch(data: RealmResults<Repo>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(owner)
            }

            override fun loadFromDb(): LiveRealmData<Repo> {
                return repoDao.loadRepositories(owner)
            }

            override fun createCall(): LiveData<ApiResponse<List<Repo>>> {
                return webService.getRepos(owner)
            }

            override fun onFetchFailed() {
                repoListRateLimit.reset(owner)
            }
        }.asLiveData()
    }


}