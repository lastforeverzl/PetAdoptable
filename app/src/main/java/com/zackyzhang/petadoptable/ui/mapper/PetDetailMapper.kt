package com.zackyzhang.petadoptable.ui.mapper

import com.zackyzhang.petadoptable.presentation.model.PetDetailView
import com.zackyzhang.petadoptable.ui.model.PetDetailViewModel
import javax.inject.Inject

/**
 * Map a [PetDetailView] to and from a [PetDetailViewModel] instance when data is moving between
 * this layer and the Domain layer
 */
open class PetDetailMapper @Inject constructor() : Mapper<PetDetailViewModel, PetDetailView> {


    /**
     * Map a [PetDetailView] instance to a [PetDetailViewModel] instance
     */
    override fun mapToViewModel(type: PetDetailView): PetDetailViewModel {
        return PetDetailViewModel(type.petStatus, type.petCityState, type.petAge, type.petSize,
                type.petMedias, type.petId, type.petBreeds, type.petName, type.petSex, type.petDescription,
                type.petMix, type.petShelterId, type.petLastUpdate, type.petAnimal, type.petIsFavorite,
                type.shelterName, type.shelterPhone, type.shelterEmail, type.shelterLongitude, type.shelterLatitude,
                type.petShelterId, type.shelterAddress)
    }

    override fun mapFromViewModel(type: PetDetailViewModel): PetDetailView {
        return PetDetailView(type.petStatus, type.petCityState, type.petAge, type.petSize,
                type.petMedias, type.petId, type.petBreeds, type.petName, type.petSex, type.petDescription,
                type.petMix, type.petShelterId, type.petLastUpdate, type.petAnimal, type.petIsFavorite,
                type.shelterName, type.shelterPhone, type.shelterEmail, type.shelterLongitude, type.shelterLatitude,
                type.petShelterId, type.shelterAddress)
    }

}