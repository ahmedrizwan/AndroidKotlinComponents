package com.snappymob.kotlincomponents

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.snappymob.kotlincomponents.retrofit.LiveDataCallAdapterFactory
import com.snappymob.kotlincomponents.retrofit.WebService
import io.realm.Realm
import io.realm.RealmObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ahmedrizwan on 9/19/17.
 * Global dependencies
 */
object Dependencies {

    private var webService: WebService? = null

    fun getRetrofit(): WebService {
        if (webService == null) {
            val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
                override fun shouldSkipField(f: FieldAttributes): Boolean {
                    return f.declaringClass == RealmObject::class.java
                }

                override fun shouldSkipClass(clazz: Class<*>): Boolean {
                    return false
                }
            }).create()

            webService = Retrofit.Builder()
                    //TODO: Update Api URL
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()
                    .create(WebService::class.java)
        }
        return webService!!
    }

    fun getDatabase(): Realm {
        return Realm.getDefaultInstance()
    }
}
