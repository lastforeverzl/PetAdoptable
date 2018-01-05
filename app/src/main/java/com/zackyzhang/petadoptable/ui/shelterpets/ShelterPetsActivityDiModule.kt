package com.zackyzhang.petadoptable.ui.shelterpets

import com.zackyzhang.petadoptable.domain.interactor.browse.GetShelterPets
import com.zackyzhang.petadoptable.presentation.browse.BrowseShelterPetsViewModelFactory
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.di.scopes.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by lei on 1/3/18.
 */
@Module
class ShelterPetsActivityDiModule {

    @Provides
    @PerActivity
    fun provideShelterPetsViewModel(getPets: GetShelterPets, petMapper: PetMapper):
            BrowseShelterPetsViewModelFactory {
        return BrowseShelterPetsViewModelFactory(getPets, petMapper)
    }
}