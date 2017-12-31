package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetById
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper

open class BrowsePetViewModelFactory(private val getPetById: GetPetById,
                                     private val petMapper: PetMapper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowsePetViewModel::class.java)) {
            return BrowsePetViewModel(getPetById, petMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}