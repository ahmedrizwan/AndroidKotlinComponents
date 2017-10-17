package com.snappymob.kotlincomponents.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.ArrayMap

/**
 * Created by ahmedrizwan on 9/9/17.
 * Helper ViewModelFactory for generating ViewModels
 * link: https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample
 */
class ViewModelFactory
constructor(private val creators: ArrayMap<Class<out ViewModel>, ViewModel>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: ViewModel? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class " + modelClass)
        }
        try {
            return creator as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}