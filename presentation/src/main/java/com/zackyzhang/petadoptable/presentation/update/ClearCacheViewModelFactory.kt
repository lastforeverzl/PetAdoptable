package com.zackyzhang.petadoptable.presentation.update

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zackyzhang.petadoptable.domain.interactor.update.ClearCache

/**
 * Created by lei on 1/10/18.
 */
open class ClearCacheViewModelFactory(private val clearCache: ClearCache) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClearCacheViewModel::class.java)) {
            return ClearCacheViewModel(clearCache) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}