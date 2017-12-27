package com.zackyzhang.petadoptable.presentation.mapper

import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.presentation.model.PetView
import javax.inject.Inject

/**
 * Map a [PetView] to and from a [Pet] instance when data is moving between
 * this layer and the Domain layer
 */
open class PetMapper @Inject constructor(): Mapper<PetView, Pet> {

    /**
     * Map a [Pet] instance to a [PetView] instance
     */
    override fun mapToView(type: Pet): PetView {
        return PetView(type.status, type.cityState, type.age, type.size, type.medias, type.id,
                type.breeds, type.name, type.sex, type.description, type.mix, type.shelterId,
                type.lastUpdate, type.animal, type.isFavorite)
    }

}
