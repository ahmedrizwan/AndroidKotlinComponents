package com.snappymob.kotlincomponents.di

import android.app.Application
import android.arch.persistence.room.Room
import com.snappymob.kotlincomponents.db.AppDb
import com.snappymob.kotlincomponents.db.RepoDao
import com.snappymob.kotlincomponents.retrofit.LiveDataCallAdapterFactory
import com.snappymob.kotlincomponents.retrofit.WebService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
/**
 * https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample/app/src/main/java/com/android/example/github/di
 * TODO: Modify dependencies here!
 */
@Module(includes = arrayOf(ViewModelModule::class))
internal class AppModule {

    @Singleton
    @Provides
    fun provideGithubService(): WebService {
        return Retrofit.Builder()
                //TODO: Update Api URL
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(WebService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDb {
        //TODO: Update Database name
        return Room.databaseBuilder(app, AppDb::class.java, "app-db").build()
    }


    @Singleton
    @Provides
    fun provideRepoDao(db: AppDb): RepoDao {
        return db.repoDao()
    }

}