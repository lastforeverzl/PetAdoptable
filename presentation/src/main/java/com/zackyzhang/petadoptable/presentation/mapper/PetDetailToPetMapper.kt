package com.zackyzhang.petadoptable.presentation.mapper

import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.presentation.model.PetDetailView
import javax.inject.Inject

open class PetDetailToPetMapper @Inject constructor() : Mapper<Pet, PetDetailView> {

    override fun mapToView(type: PetDetailView): Pet {
        return Pet(type.petStatus, type.petCityState, type.petAge, type.petSize, type.petMedias,
                type.petId, type.petBreeds, type.petName, type.petSex, type.petDescription, type.petMix,
                type.petShelterId, type.petLastUpdate, type.petAnimal, type.petIsFavorite)
    }

}