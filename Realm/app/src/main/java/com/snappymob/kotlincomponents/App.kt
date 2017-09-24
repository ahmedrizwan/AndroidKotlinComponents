package com.snappymob.kotlincomponents

import android.app.Application
import com.snappymob.kotlincomponents.network.AppExecutors
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by ahmedrizwan on 9/9/17.
 */

class App : Application() {
    lateinit var appExecutors: AppExecutors
    lateinit var db: Realm

    override fun onCreate() {
        super.onCreate()
        appExecutors = AppExecutors()
//        appExecutors.diskIO().execute({
        Realm.init(applicationContext)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build())
        db = Realm.getDefaultInstance()
//        })
    }
}
