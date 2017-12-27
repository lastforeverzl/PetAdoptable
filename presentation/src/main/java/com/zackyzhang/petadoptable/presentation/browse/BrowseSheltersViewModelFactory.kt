package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zackyzhang.petadoptable.domain.interactor.browse.GetShelters
import com.zackyzhang.petadoptable.presentation.mapper.ShelterMapper

open class BrowseSheltersViewModelFactory(private val getShelters: GetShelters,
                                      private val petMapper: ShelterMapper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowseSheltersViewModel::class.java)) {
            return BrowseSheltersViewModel(getShelters, petMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}