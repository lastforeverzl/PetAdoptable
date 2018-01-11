package com.zackyzhang.petadoptable.domain.mapper

import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.model.PetDetail
import com.zackyzhang.petadoptable.domain.model.Shelter
import javax.inject.Inject

/**
 * Map a [PetView] to and from a [Pet] instance when data is moving between
 * this layer and the Domain layer
 */
open class PetDetailMapper @Inject constructor(): Mapper<PetDetail, Pet, Shelter> {

    /**
     * Map a [Pet] instance to a [PetDetail] instance
     */
    override fun mapToPetDetail(pet: Pet, shelter: Shelter): PetDetail {
        return PetDetail(pet.status, pet.cityState, pet.age, pet.size, pet.medias, pet.id, pet.breeds,
                pet.name, pet.sex, pet.description, pet.mix, pet.shelterId, pet.lastUpdate, pet.animal,
                pet.isFavorite,
                shelter.country, shelter.longitude, shelter.name, shelter.phone, shelter.state,
                shelter.address2, shelter.email, shelter.city, shelter.zip, shelter.fax, shelter.latitude,
                shelter.id, shelter.address1)
    }

}
