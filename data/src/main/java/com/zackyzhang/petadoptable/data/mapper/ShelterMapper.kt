package com.zackyzhang.petadoptable.data.mapper

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.domain.model.Shelter
import javax.inject.Inject

/**
 * Map a [ShelterEntity] to and from a [Shelter] instance when data is moving between
 * this layer and the Domain layer
 */
open class ShelterMapper @Inject constructor(): Mapper<ShelterEntity, Shelter> {

    /**
     * Map a [ShelterEntity] instance to a [Shelter] instance
     */
    override fun mapFromEntity(type: ShelterEntity): Shelter {
        return Shelter(type.country, type.longitude, type.name, type.phone,type.state, type.address2,
                type.email, type.city, type.zip, type.fax, type.latitude, type.id, type.address1)
    }

    /**
     * Map a [Shelter] instance to a [ShelterEntity] instance
     */
    override fun mapToEntity(type: Shelter): ShelterEntity {
        return ShelterEntity(type.country, type.longitude, type.name, type.phone,type.state,
                type.address2, type.email, type.city, type.zip, type.fax, type.latitude, type.id,
                type.address1)
    }
}