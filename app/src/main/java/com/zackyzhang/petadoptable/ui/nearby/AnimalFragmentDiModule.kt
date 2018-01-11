package com.zackyzhang.petadoptable.ui.nearby

import com.zackyzhang.petadoptable.domain.interactor.browse.GetPets
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetsViewModelFactory
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.di.scopes.PerChildFragment
import dagger.Module
import dagger.Provides

@Module
class AnimalFragmentDiModule {

    @Provides
    @PerChildFragment
    fun providePetsViewModelFactory(getPets: GetPets, petMapper: PetMapper):
            BrowsePetsViewModelFactory {
        return BrowsePetsViewModelFactory(getPets, petMapper)
    }

}