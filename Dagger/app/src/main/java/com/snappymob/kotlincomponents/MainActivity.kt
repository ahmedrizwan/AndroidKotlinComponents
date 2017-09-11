package com.snappymob.kotlincomponents

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.snappymob.kotlincomponents.adapters.ReposAdapter
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.Status
import com.snappymob.kotlincomponents.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject








class MainActivity : LifecycleActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        viewModelFactory.let {
            val repoViewModel = ViewModelProviders.of(this, it).get(RepoViewModel::class.java)
            val reposAdapter = ReposAdapter(this, ArrayList())
            recyclerViewRepos.adapter = reposAdapter
            recyclerViewRepos.layoutManager = LinearLayoutManager(this)

            buttonSearch.setOnClickListener({
                if (editTextUser.text.length > 3) {
                    repoViewModel.loadRepos(editTextUser.text.toString())?.observe(this, Observer {
                        it?.let {
                            when (it.status) {
                                Status.SUCCESS -> {
                                    Log.e("Status", "Success")
                                    reposAdapter.updateDataSet(it.data as ArrayList<Repo>)
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
