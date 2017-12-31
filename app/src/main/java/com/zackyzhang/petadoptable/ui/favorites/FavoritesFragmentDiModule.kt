package com.zackyzhang.petadoptable.ui.favorites

import com.zackyzhang.petadoptable.domain.interactor.browse.GetFavoritePets
import com.zackyzhang.petadoptable.presentation.browse.BrowseFavoritePetsViewModelFactory
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.di.scopes.PerFragment
import dagger.Module
import dagger.Provides

/**
 * Created by lei on 12/18/17.
 */
@Module
class FavoritesFragmentDiModule {

    @Provides
    @PerFragment
    fun provideFavoritesViewModelFactory(getFavoritePets: GetFavoritePets, petMapper: PetMapper):
            BrowseFavoritePetsViewModelFactory {
        return BrowseFavoritePetsViewModelFactory(getFavoritePets, petMapper)
    }

}