@file:JvmName("RealmUtils") // pretty name for utils class if called from
package com.snappymob.kotlincomponents.utils

import com.snappymob.kotlincomponents.db.RepoDao
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

/**
 * Created by ahmedrizwan on 9/18/17.
 * Helper Extension methods for Realm
 */
fun Realm.repoDao(): RepoDao = RepoDao(this)

fun <T:RealmModel> RealmResults<T>.asLiveData() = LiveRealmData(this)