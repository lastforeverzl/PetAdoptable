package com.zackyzhang.petadoptable.presentation.update

import com.zackyzhang.petadoptable.domain.interactor.update.ClearCache
import io.reactivex.observers.DisposableCompletableObserver
import javax.inject.Inject

/**
 * Created by lei on 1/10/18.
 */
class ClearCacheJob @Inject constructor(private val clearCache: ClearCache) {

    fun clearCache() {
        clearCache.execute(ClearSubscriber(), Unit)
    }

    inner class ClearSubscriber : DisposableCompletableObserver() {

        override fun onComplete() {
            println("ClearCacheJob: onComplete")
        }

        override fun onError(e: Throwable) {
            println("ClearCacheJob: ${e.message}")
        }

    }
}
