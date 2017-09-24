package com.snappymob.kotlincomponents

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.ArrayMap
import android.view.View
import android.widget.Toast
import com.snappymob.kotlincomponents.adapters.ReposAdapter
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.Status
import com.snappymob.kotlincomponents.repository.RepoRepository
import com.snappymob.kotlincomponents.retrofit.GithubService
import com.snappymob.kotlincomponents.retrofit.LiveDataCallAdapterFactory
import com.snappymob.kotlincomponents.utils.repoModel
import com.snappymob.kotlincomponents.viewmodel.RepoViewModel
import com.snappymob.kotlincomponents.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/***
 * Activity that displays a list of Repos
 */
class MainActivity : AppCompatActivity() {

    private lateinit var repoViewModel: RepoViewModel

    private val USER_STATE_KEY = "UserName"

    private fun getGithubService(): GithubService {
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

        val arrayMap = ArrayMap<Class<out ViewModel>, ViewModel>()

        val githubDb = (application as App).db

        val appExecutors = (application as App).appExecutors

        arrayMap.put(RepoViewModel::class.java, RepoViewModel(RepoRepository(githubDb.repoModel(), retrofit, appExecutors)))

        val factory = ViewModelFactory(arrayMap)

        repoViewModel = ViewModelProviders.of(this, factory).get(RepoViewModel::class.java)

        val reposAdapter = ReposAdapter(this, ArrayList())
        recyclerViewRepos.adapter = reposAdapter
        recyclerViewRepos.layoutManager = LinearLayoutManager(this)

        //search click listener
        setupSearchListener(reposAdapter)

        //state recovery using viewModel
        recoverState(savedInstanceState, reposAdapter)
    }

    private fun setupSearchListener(reposAdapter: ReposAdapter) {
        buttonSearch.setOnClickListener({
            if (editTextUser.text.length > 3) {
                repoViewModel.loadRepos(editTextUser.text.toString())?.observe(this, Observer {
                    it?.let {
                        textViewError.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        reposAdapter.updateDataSet(ArrayList())
                        when (it.status) {
                            Status.SUCCESS -> {
                                recyclerViewRepos.visibility = View.VISIBLE
                                it.data?.let {
                                    reposAdapter.updateDataSet(it.toList() as ArrayList<Repo>)
                                }
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
            } else {
                Toast.makeText(this, "Repo name must be > 3 length", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun recoverState(savedInstanceState: Bundle?, reposAdapter: ReposAdapter) {
        val currentUserName = savedInstanceState?.get(USER_STATE_KEY) as String?
        currentUserName?.let {
            repoViewModel.loadRepos(it)?.observe(this, Observer {
                it?.let {
                    textViewError.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    reposAdapter.updateDataSet(ArrayList())
                    when (it.status) {
                        Status.SUCCESS -> {
                            recyclerViewRepos.visibility = View.VISIBLE
                            it.data?.let {
                                reposAdapter.updateDataSet(it.toList() as ArrayList<Repo>)
                            }
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
    }

    //save state
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        //get the state from viewModel
        outState?.putString(USER_STATE_KEY, repoViewModel.currentRepoUser)
    }
}
