package com.zackyzhang.petadoptable.presentation.mapper

import com.zackyzhang.petadoptable.domain.model.PetDetail
import com.zackyzhang.petadoptable.presentation.model.PetDetailView
import javax.inject.Inject

/**
 * Map a [PetDetailView] to and from a [PetDetail] instance when data is moving between
 * this layer and the Domain layer
 */
open class PetDetailMapper @Inject constructor(): Mapper<PetDetailView, PetDetail> {

    /**
     * Map a [PetDetail] instance to a [PetDetailView] instance
     */
    override fun mapToView(type: PetDetail): PetDetailView {
        val shelterAddress = "${type.shelterAddress1} ${type.shelterAddress2} ${type.shelterCity} " +
                "${type.shelterState} ${type.shelterZip} ${type.shelterCountry}".trim()

        return PetDetailView(type.petStatus, type.petCityState, type.petAge, type.petSize, type.petMedias,
                type.petId, type.petBreeds, type.petName, type.petSex, type.petDescription, type.petMix,
                type.petShelterId, type.petLastUpdate, type.petAnimal, type.petIsFavorite, type.shelterName,
                type.shelterPhone.trim(), type.shelterEmail, type.shelterLongitude, type.shelterLatitude,
                type.shelterId, shelterAddress)
    }

}