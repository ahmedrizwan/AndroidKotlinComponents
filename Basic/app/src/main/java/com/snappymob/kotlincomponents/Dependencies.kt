package com.snappymob.kotlincomponents

import android.arch.persistence.room.Room
import android.content.Context
import com.snappymob.kotlincomponents.db.AppDb
import com.snappymob.kotlincomponents.retrofit.LiveDataCallAdapterFactory
import com.snappymob.kotlincomponents.retrofit.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ahmedrizwan on 9/19/17.
 *
 */
object Dependencies {

    var retrofitService: RetrofitService? = null
    var db: AppDb? = null

    fun getRetrofit(): RetrofitService {
        if (retrofitService == null) {
            retrofitService = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()
                    .create(RetrofitService::class.java)
        }
        return retrofitService!!
    }

    fun getDatabase(context: Context): AppDb {
        if (db == null) {
            db = Room.databaseBuilder(context,
                    AppDb::class.java, "app-db")
                    .fallbackToDestructiveMigration()
                    .build()
        }
        return db!!
    }
}