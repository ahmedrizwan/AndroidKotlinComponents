package com.snappymob.kotlincomponents

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.widget.Toast
import com.snappymob.kotlincomponents.db.GithubDb
import com.snappymob.kotlincomponents.network.AppExecutors
import com.snappymob.kotlincomponents.network.Status
import com.snappymob.kotlincomponents.repository.RepoRepository
import com.snappymob.kotlincomponents.retrofit.GithubService
import com.snappymob.kotlincomponents.retrofit.LiveDataCallAdapterFactory
import com.snappymob.kotlincomponents.viewmodel.GithubViewModelFactory
import com.snappymob.kotlincomponents.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : LifecycleActivity() {

    fun getGithubService(): GithubService? {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(GithubService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = getGithubService()

        if(retrofit!=null) {
            val githubDb = Room.databaseBuilder(applicationContext,
                    GithubDb::class.java, "app-db").allowMainThreadQueries()
                    .build()

            val appExecutors = AppExecutors()

            val repo = RepoViewModel(RepoRepository(githubDb.repoDao(), retrofit, appExecutors))
//            val provider = Provider<RepoViewModel>(function = {repo})
            val arrayMap = ArrayMap<Class<out ViewModel>, ViewModel>()
            arrayMap.put(RepoViewModel::class.java, repo)

            val factory = GithubViewModelFactory(arrayMap)
            val repoViewModel = ViewModelProviders.of(this, factory).get(RepoViewModel::class.java)
            buttonSearch.setOnClickListener({
                if (editTextUser.text.length > 3) {
                    repoViewModel.loadRepos(editTextUser.text.toString())?.observe(this, Observer {
                        it?.let {
                            when (it.status) {
                                Status.SUCCESS -> {
                                    Log.e("Status", "Success")
                                }
                                Status.ERROR -> {
                                    Log.e("Status", "Error ${it.message}")
                                }
                                Status.LOADING -> {
                                    Log.e("Status", "Loading")
                                }
                            }
                        }
                    })
                } else {
                   Toast.makeText(this, "Repo name must be > 3 length", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
