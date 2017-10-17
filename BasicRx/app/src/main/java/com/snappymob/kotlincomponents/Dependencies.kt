package com.snappymob.kotlincomponents

import android.arch.persistence.room.Room
import android.content.Context
import com.snappymob.kotlincomponents.db.AppDb
import com.snappymob.kotlincomponents.retrofit.WebService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ahmedrizwan on 9/19/17.
 * Global dependencies
 */
object Dependencies {

    var webService: WebService? = null
    var db: AppDb? = null

    fun getRetrofit(): WebService {
        if (webService == null) {
            webService = Retrofit.Builder()
                    //TODO: Update Api URL
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(WebService::class.java)
        }
        return webService!!
    }

    fun getDatabase(context: Context): AppDb {
        if (db == null) {
            db = Room.databaseBuilder(context,
                    //TODO: Update Database name
                    AppDb::class.java, "app-db")
                    .fallbackToDestructiveMigration()
                    .build()
        }
        return db!!
    }
}