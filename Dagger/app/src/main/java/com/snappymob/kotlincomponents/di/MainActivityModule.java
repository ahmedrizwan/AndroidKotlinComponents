package com.snappymob.kotlincomponents.di;

import com.snappymob.kotlincomponents.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

}