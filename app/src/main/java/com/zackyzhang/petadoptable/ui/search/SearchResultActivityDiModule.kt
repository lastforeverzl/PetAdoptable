package com.zackyzhang.petadoptable.ui.search

import com.zackyzhang.petadoptable.domain.interactor.browse.GetSearchPets
import com.zackyzhang.petadoptable.presentation.browse.BrowseSearchViewModelFactory
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.di.scopes.PerActivity
import dagger.Module
import dagger.Provides

@Module
class SearchResultActivityDiModule {

    @Provides
    @PerActivity
    fun provideSearchViewModel(getPets: GetSearchPets, petMapper: PetMapper):
            BrowseSearchViewModelFactory {
        return BrowseSearchViewModelFactory(getPets, petMapper)
    }
}