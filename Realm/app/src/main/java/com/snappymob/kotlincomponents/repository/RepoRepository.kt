package com.snappymob.kotlincomponents.repository

import android.arch.lifecycle.LiveData
import android.util.Log
import com.snappymob.kotlincomponents.db.RepoDao
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.ApiResponse
import com.snappymob.kotlincomponents.network.AppExecutors
import com.snappymob.kotlincomponents.network.NetworkBoundResource
import com.snappymob.kotlincomponents.network.Resource
import com.snappymob.kotlincomponents.retrofit.GithubService
import com.snappymob.kotlincomponents.utils.LiveRealmData
import com.snappymob.kotlincomponents.utils.RateLimiter
import io.realm.RealmResults
import java.util.concurrent.TimeUnit


/**
 * Created by ahmedrizwan on 9/10/17.
 *
 */
class RepoRepository(val repoDao: RepoDao?, val githubService: GithubService, val appExecutors: AppExecutors) {
    val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(owner: String): LiveData<Resource<RealmResults<Repo>>> {
        return object : NetworkBoundResource<Repo, List<Repo>>(appExecutors) {
            override fun saveCallResult(item: List<Repo>) {
                Log.e("Save",item.size.toString())
                repoDao?.insertRepos(item)
            }

            override fun shouldFetch(data: RealmResults<Repo>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(owner)
            }

            override fun loadFromDb(): LiveRealmData<Repo> {
                Log.e("LoadDb", "Here")

                return repoDao!!.loadRepositories(owner)
            }

            override fun createCall(): LiveData<ApiResponse<List<Repo>>> {
                return githubService.getRepos(owner)
            }


            override fun onFetchFailed() {
                repoListRateLimit.reset(owner)
            }
        }.asLiveData()
    }


}