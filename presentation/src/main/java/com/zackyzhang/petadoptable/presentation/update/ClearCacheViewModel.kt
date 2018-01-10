package com.zackyzhang.petadoptable.presentation.update

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zackyzhang.petadoptable.domain.interactor.update.ClearCache
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

/**
 * Created by lei on 1/10/18.
 */
open class ClearCacheViewModel @Inject constructor(private val clearCache: ClearCache): ViewModel() {

    val clearCacheLiveData: MutableLiveData<Resource<Unit>> = MutableLiveData()

    fun getLiveData(): LiveData<Resource<Unit>> {
        return clearCacheLiveData
    }

    fun clearCache() {
        clearCache.execute(ClearSubscriber(), Unit)
    }

    inner class ClearSubscriber : DisposableCompletableObserver() {

        override fun onComplete() {
            clearCacheLiveData
                    .postValue(Resource(ResourceState.SUCCESS, null, "cleared"))
        }

        override fun onError(e: Throwable) {
            clearCacheLiveData
                    .postValue(Resource(ResourceState.ERROR, null, "error"))
        }

    }
}