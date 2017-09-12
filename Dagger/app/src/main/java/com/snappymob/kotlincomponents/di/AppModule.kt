package com.snappymob.kotlincomponents.di

import android.app.Application
import android.arch.persistence.room.Room
import com.snappymob.kotlincomponents.db.GithubDb
import com.snappymob.kotlincomponents.db.RepoDao
import com.snappymob.kotlincomponents.retrofit.GithubService
import com.snappymob.kotlincomponents.retrofit.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
        includes = arrayOf(ViewModelModule::class)
)
internal class AppModule {

    @Singleton
    @Provides
    fun provideGithubService(): GithubService {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDb {
        return Room.databaseBuilder(app, GithubDb::class.java, "app-db").build()
    }


    @Singleton
    @Provides
    fun provideRepoDao(db: GithubDb): RepoDao {
        return db.repoDao()
    }
    
}