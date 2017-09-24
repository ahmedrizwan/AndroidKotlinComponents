@file:JvmName("RealmUtils") // pretty name for utils class if called from
package com.snappymob.kotlincomponents.utils

import com.snappymob.kotlincomponents.db.RepoDao
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

fun Realm.repoModel(): RepoDao = RepoDao(this)

fun <T:RealmModel> RealmResults<T>.asLiveData() = LiveRealmData(this)