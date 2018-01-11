package com.zackyzhang.petadoptable.ui.favorites

import com.zackyzhang.petadoptable.domain.interactor.browse.GetFavoritePets
import com.zackyzhang.petadoptable.presentation.browse.BrowseFavoritePetsViewModelFactory
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.di.scopes.PerFragment
import dagger.Module
import dagger.Provides

@Module
class FavoritesFragmentDiModule {

    @Provides
    @PerFragment
    fun provideFavoritesViewModelFactory(getFavoritePets: GetFavoritePets, petMapper: PetMapper):
            BrowseFavoritePetsViewModelFactory {
        return BrowseFavoritePetsViewModelFactory(getFavoritePets, petMapper)
    }

}