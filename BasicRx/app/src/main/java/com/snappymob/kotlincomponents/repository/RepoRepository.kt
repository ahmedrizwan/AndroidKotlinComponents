package com.snappymob.kotlincomponents.repository

import android.support.annotation.Nullable
import android.util.Log
import com.snappymob.kotlincomponents.db.RepoDao
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.ApiResponse
import com.snappymob.kotlincomponents.network.AppExecutors
import com.snappymob.kotlincomponents.network.NetworkBoundResource
import com.snappymob.kotlincomponents.network.Resource
import com.snappymob.kotlincomponents.retrofit.GithubService
import com.snappymob.kotlincomponents.utils.RateLimiter
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit


/**
 * Created by ahmedrizwan on 9/10/17.
 *
 */
class RepoRepository(val repoDao: RepoDao, val githubService: GithubService, val appExecutors: AppExecutors) {
    val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(owner: String): Flowable<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, List<Repo>>(appExecutors) {
            override fun saveCallResult(item: List<Repo>) {
                Log.e("Success", item.size.toString())

                repoDao.insertRepos(item)
            }

            override fun shouldFetch(@Nullable data: List<Repo>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(owner)
            }

            override fun loadFromDb(): Flowable<List<Repo>> {
                return repoDao.loadRepositories(owner)
            }

            override fun createCall(): Flowable<ApiResponse<List<Repo>>> {
                return githubService.getRepos(owner)
            }

            override fun onFetchFailed() {
                repoListRateLimit.reset(owner)
            }
        }.asFlowable()
    }

}