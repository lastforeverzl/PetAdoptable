package com.zackyzhang.petadoptable.ui.detail

import com.zackyzhang.petadoptable.domain.interactor.browse.GetFavoriteStatus
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetDetailInfo
import com.zackyzhang.petadoptable.domain.interactor.update.UpdateFavoritePet
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetViewModelFactory
import com.zackyzhang.petadoptable.presentation.browse.CheckFavoriteStatusViewModelFactory
import com.zackyzhang.petadoptable.presentation.mapper.PetDetailMapper
import com.zackyzhang.petadoptable.presentation.mapper.PetDetailToPetMapper
import com.zackyzhang.petadoptable.ui.di.scopes.PerActivity
import dagger.Module
import dagger.Provides

@Module
class PetDetailActivityDiModule {

    @Provides
    @PerActivity
    fun providePetViewModel(getPetDetailInfo: GetPetDetailInfo,
                            updateFavoritePet: UpdateFavoritePet,
                            petDetailMapper: PetDetailMapper,
                            petDetailToPetMapper: PetDetailToPetMapper): BrowsePetViewModelFactory{
        return BrowsePetViewModelFactory(getPetDetailInfo, updateFavoritePet, petDetailMapper, petDetailToPetMapper)
    }

    @Provides
    @PerActivity
    fun provideCheckFavoriteStatusViewModel(getFavoriteStatus: GetFavoriteStatus):
            CheckFavoriteStatusViewModelFactory {
        return CheckFavoriteStatusViewModelFactory(getFavoriteStatus)
    }
}