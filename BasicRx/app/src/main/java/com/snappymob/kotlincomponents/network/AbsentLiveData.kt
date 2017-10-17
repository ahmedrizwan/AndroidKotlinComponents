package com.snappymob.kotlincomponents.network

import com.snappymob.kotlincomponents.repository.RepoRepository
import com.snappymob.kotlincomponents.viewmodel.RepoLiveData

/**
 * Created by ahmedrizwan on 9/9/17.
 * Helper class for transmitting an empty LiveData - Pretty useful!
 */
class AbsentLiveData private constructor(repository: RepoRepository, string: String) : RepoLiveData(repository, string) {
    init {
        postValue(null)
    }

    companion object {
        fun create(repository: RepoRepository): AbsentLiveData {
            return AbsentLiveData(repository, "")
        }
    }
}
