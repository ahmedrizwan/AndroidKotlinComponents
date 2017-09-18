package com.snappymob.kotlincomponents.network

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import android.util.Log
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor(private val appExecutors: AppExecutors) {

    private val result = PublishSubject.create<Resource<ResultType>>()

    init {
        result.onNext(Resource.loading(null))

        val dbSource = loadFromDb()
        dbSource.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value ->
                    if (shouldFetch(value)) {
                        fetchFromNetwork(dbSource)
                    } else {
                        result.onNext(Resource.success(value))
                    }
                })
    }

    fun fetchFromNetwork(dbSource: Flowable<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
//        dbSource.subscribe({ value ->
//            result.onNext(Resource.success(value))
//        })
        result.onNext(Resource.loading(null))
        apiResponse.subscribeOn(Schedulers.from(appExecutors.networkIO()))
                .observeOn(Schedulers.from(appExecutors.mainThread()))
                .subscribe({ response ->
                    if (response!=null) {
                        appExecutors
                                .diskIO()
                                .execute {
//                                    processResponse(response)?.let { saveCallResult(it) }
                                    saveCallResult(response)
                                    appExecutors.mainThread()
                                            .execute {
                                                // we specially request a new live data,
                                                // otherwise we will get immediately last cached value,
                                                // which may not be updated with latest results received from network.
                                                loadFromDb().subscribeOn(Schedulers.from(appExecutors.networkIO()))
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe({
                                                            result.onNext(Resource.success(it))
                                                        })
                                            }
                                }
                    } else {
                        onFetchFailed()
                        Log.e("Failed", "Fetch")

//                        result.onNext(Resource.error())
//                        result.addSource(dbSource
//                        ) { resultType -> result.value = response.errorMessage?.let { Resource.error(it, resultType) } }
                    }
                })

    }
    fun asFlowable(): Flowable<Resource<ResultType>> {
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }
    protected open fun onFetchFailed() {}

    @WorkerThread
    protected fun processResponse(response: ApiResponse<RequestType>): RequestType? {
        return response.body
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flowable<ResultType>

    @MainThread
    protected abstract fun createCall(): Flowable<RequestType>

}