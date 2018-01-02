package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetDetailInfo
import com.zackyzhang.petadoptable.domain.interactor.update.UpdateFavoritePet
import com.zackyzhang.petadoptable.presentation.mapper.PetDetailMapper
import com.zackyzhang.petadoptable.presentation.mapper.PetDetailToPetMapper

open class BrowsePetViewModelFactory(private val getPetDetailInfo: GetPetDetailInfo,
                                     private val updateFavoritePet: UpdateFavoritePet,
                                     private val petDetailMapper: PetDetailMapper,
                                     private val petDetailToPetMapper: PetDetailToPetMapper) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowsePetViewModel::class.java)) {
            return BrowsePetViewModel(getPetDetailInfo, updateFavoritePet, petDetailMapper, petDetailToPetMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}