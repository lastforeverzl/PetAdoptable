package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zackyzhang.petadoptable.domain.interactor.browse.GetFavoritePets
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper

open class BrowseFavoritePetsViewModelFactory(private val getFavoritePets: GetFavoritePets,
                                      private val petMapper: PetMapper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowseFavoritePetsViewModel::class.java)) {
            return BrowseFavoritePetsViewModel(getFavoritePets, petMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}