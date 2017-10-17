package com.snappymob.kotlincomponents.viewmodel

import android.arch.lifecycle.LiveData
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.Resource
import com.snappymob.kotlincomponents.repository.RepoRepository
import io.reactivex.disposables.Disposable

/**
 * Created by ahmedrizwan on 9/18/17.
 */

open class RepoLiveData(repoRepository: RepoRepository, owner:String):
        LiveData<Resource<List<Repo>>>() {
    private var disposable: Disposable? = null

    init {
        disposable = repoRepository.loadRepos(owner).subscribe({
            data ->
            value = data
        })
    }

    override fun onInactive() {
        super.onInactive()
        if(disposable?.isDisposed?.not() == true){
            disposable?.dispose()
        }
    }

}