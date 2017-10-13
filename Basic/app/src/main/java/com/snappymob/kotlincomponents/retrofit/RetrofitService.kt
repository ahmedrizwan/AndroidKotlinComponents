package com.snappymob.kotlincomponents.retrofit

import android.arch.lifecycle.LiveData
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path




/**
 * Created by ahmedrizwan on 9/9/17.
 */
interface RetrofitService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): LiveData<ApiResponse<List<Repo>>>

    @GET("users/{login}/repos")
    fun getRepos(@Path("login") login: String): LiveData<ApiResponse<List<Repo>>>

}