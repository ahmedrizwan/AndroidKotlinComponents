package com.snappymob.kotlincomponents.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.snappymob.kotlincomponents.viewmodel.ViewModelFactory
import com.snappymob.kotlincomponents.viewmodel.RepoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap



/**
 * Created by ahmedrizwan on 9/9/17.
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    internal abstract fun bindRepoViewModel(repoViewModel: RepoViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}