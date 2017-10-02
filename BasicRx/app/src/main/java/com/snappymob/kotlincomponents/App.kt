package com.snappymob.kotlincomponents

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by ahmedrizwan on 9/9/17.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
