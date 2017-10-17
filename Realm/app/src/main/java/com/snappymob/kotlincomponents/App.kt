package com.snappymob.kotlincomponents

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by ahmedrizwan on 9/9/17.
 * Application class
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //instantiate db
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build())
    }
}
