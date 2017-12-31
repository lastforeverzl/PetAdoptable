package com.zackyzhang.petadoptable.ui.detail

import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetById
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetViewModelFactory
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.di.scopes.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by lei on 12/29/17.
 */
@Module
class PetDetailActivityDiModule {

    @Provides
    @PerActivity
    fun providePetViewModel(getPetById: GetPetById, petMapper: PetMapper): BrowsePetViewModelFactory{
        return BrowsePetViewModelFactory(getPetById, petMapper)
    }
}