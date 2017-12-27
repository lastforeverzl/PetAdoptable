package com.zackyzhang.petadoptable.ui.shelters

import com.zackyzhang.petadoptable.domain.interactor.browse.GetShelters
import com.zackyzhang.petadoptable.presentation.browse.BrowseSheltersViewModelFactory
import com.zackyzhang.petadoptable.presentation.mapper.ShelterMapper
import com.zackyzhang.petadoptable.ui.di.scopes.PerFragment
import dagger.Module
import dagger.Provides

/**
 * Created by lei on 12/18/17.
 */
@Module
class SheltersFragmentDiModule {

    @Provides
    @PerFragment
    fun provideSheltersViewModelFactory(getShelters: GetShelters, shelterMapper: ShelterMapper):
            BrowseSheltersViewModelFactory {
        return BrowseSheltersViewModelFactory(getShelters, shelterMapper)
    }

}