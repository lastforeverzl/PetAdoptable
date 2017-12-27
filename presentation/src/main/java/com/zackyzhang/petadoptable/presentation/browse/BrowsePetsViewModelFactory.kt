package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPets
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper

open class BrowsePetsViewModelFactory(private val getPets: GetPets,
                                      private val petMapper: PetMapper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowsePetsViewModel::class.java)) {
            return BrowsePetsViewModel(getPets, petMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}