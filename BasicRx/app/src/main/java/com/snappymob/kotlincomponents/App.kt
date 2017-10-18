package com.snappymob.kotlincomponents

import android.app.Application

/**
 * Created by ahmedrizwan on 9/9/17.
 * Application class
 * TODO: Update package of the application (if you really want to)
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //instantiate db
        Dependencies.getDatabase(this)
    }
}
