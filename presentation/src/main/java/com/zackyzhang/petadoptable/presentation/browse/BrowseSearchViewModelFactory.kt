package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zackyzhang.petadoptable.domain.interactor.browse.GetSearchPets
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper

/**
 * Created by lei on 1/8/18.
 */
open class BrowseSearchViewModelFactory(private val getPets: GetSearchPets,
                                             private val petMapper: PetMapper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowseSearchViewModel::class.java)) {
            return BrowseSearchViewModel(getPets, petMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}