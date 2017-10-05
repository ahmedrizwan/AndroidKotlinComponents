package com.snappymob.kotlincomponents

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.ArrayMap
import android.view.View
import com.snappymob.kotlincomponents.adapters.ReposAdapter
import com.snappymob.kotlincomponents.db.GithubDb
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.AppExecutors
import com.snappymob.kotlincomponents.network.Status
import com.snappymob.kotlincomponents.repository.RepoRepository
import com.snappymob.kotlincomponents.retrofit.GithubService
import com.snappymob.kotlincomponents.retrofit.LiveDataCallAdapterFactory
import com.snappymob.kotlincomponents.viewmodel.RepoViewModel
import com.snappymob.kotlincomponents.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/***
 * Activity that displays a list of Repos
 */
class MainActivity : AppCompatActivity() {

    private lateinit var repoViewModel:RepoViewModel

    private val USER_STATE_KEY = "UserName"

    private fun getGithubService(): GithubService {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(GithubService::class.java)
    }

    private fun getDatabase():GithubDb {
        return Room.databaseBuilder(applicationContext,
                GithubDb::class.java, "app-db").allowMainThreadQueries()
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = getGithubService()

        val githubDb = getDatabase()

        val appExecutors = AppExecutors()

        val arrayMap = ArrayMap<Class<out ViewModel>, ViewModel>()

        arrayMap.put(RepoViewModel::class.java, RepoViewModel(RepoRepository(githubDb.repoDao(), retrofit, appExecutors)))

        val factory = ViewModelFactory(arrayMap)
        repoViewModel = ViewModelProviders.of(this, factory).get(RepoViewModel::class.java)

        val reposAdapter = ReposAdapter(this, ArrayList())
        recyclerViewRepos.adapter = reposAdapter
        recyclerViewRepos.layoutManager = LinearLayoutManager(this)

        //search click listener
        setupSearchListener(reposAdapter, savedInstanceState)

    }

    private fun setupSearchListener(reposAdapter: ReposAdapter, savedInstanceState: Bundle?) {
        buttonSearch.setOnClickListener({
            repoViewModel.setQuery(editTextUser.text.toString())
        })
        val currentUserName = savedInstanceState?.get(USER_STATE_KEY) as String?
        repoViewModel.setQuery(currentUserName)
        repoViewModel.results.observe(this, Observer {
            it?.let {
                textViewError.visibility = View.GONE
                progressBar.visibility = View.GONE
                reposAdapter.updateDataSet(ArrayList())
                when (it.status) {
                    Status.SUCCESS -> {
                        recyclerViewRepos.visibility = View.VISIBLE
                        reposAdapter.updateDataSet(it.data as ArrayList<Repo>)
                    }
                    Status.ERROR -> {
                        textViewError.visibility = View.VISIBLE
                        textViewError.text = it.message
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    //save state
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        //get the state from viewModel
        outState?.putString(USER_STATE_KEY, repoViewModel.currentRepoUser)
    }
}
