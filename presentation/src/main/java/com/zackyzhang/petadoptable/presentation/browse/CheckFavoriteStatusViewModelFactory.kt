package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zackyzhang.petadoptable.domain.interactor.browse.GetFavoriteStatus

/**
 * Created by lei on 1/2/18.
 */
open class CheckFavoriteStatusViewModelFactory(private val getFavoriteStatus: GetFavoriteStatus) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckFavoriteStatusViewModel::class.java)) {
            return CheckFavoriteStatusViewModel(getFavoriteStatus) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}