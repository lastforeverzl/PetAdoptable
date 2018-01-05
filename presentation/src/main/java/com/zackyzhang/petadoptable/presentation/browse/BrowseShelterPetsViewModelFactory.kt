package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zackyzhang.petadoptable.domain.interactor.browse.GetShelterPets
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper

open class BrowseShelterPetsViewModelFactory(private val getPets: GetShelterPets,
                                      private val petMapper: PetMapper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowseShelterPetsViewModel::class.java)) {
            return BrowseShelterPetsViewModel(getPets, petMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
