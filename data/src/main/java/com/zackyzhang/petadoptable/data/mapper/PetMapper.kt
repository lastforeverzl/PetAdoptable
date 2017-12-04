package com.zackyzhang.petadoptable.data.mapper

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.domain.model.Pet
import javax.inject.Inject

/**
 * Map a [PetEntity] to and from a [Pet] instance when data is moving between
 * this layer and the Domain layer
 */
open class PetMapper @Inject constructor(): Mapper<PetEntity, Pet> {

    /**
     * Map a [PetEntity] instance to a [Pet] instance
     */
    override fun mapFromEntity(type: PetEntity): Pet {
        return Pet(type.status, type.cityState, type.age, type.size, type.media, type.id, type.breeds,
                type.name, type.sex, type.description, type.mix, type.shelterId, type.lastUpdate, type.animal)
    }

    /**
     * Map a [Pet] instance to a [PetEntity] instance
     */
    override fun mapToEntity(type: Pet): PetEntity {
        return PetEntity(type.status, type.cityState, type.age, type.size, type.media, type.id, type.breeds,
                type.name, type.sex, type.description, type.mix, type.shelterId, type.lastUpdate, type.animal)
    }
}