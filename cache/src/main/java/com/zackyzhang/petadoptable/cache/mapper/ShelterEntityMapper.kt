package com.zackyzhang.petadoptable.cache.mapper

import com.zackyzhang.petadoptable.cache.db.entity.ShelterDbEntity
import com.zackyzhang.petadoptable.data.model.ShelterEntity
import javax.inject.Inject

/**
 * Map a [ShelterDbEntity] instance to and from a [ShelterEntity] instance when data is moving between
 * this layer and the Data layer
 */
class ShelterEntityMapper @Inject constructor(): EntityMapper<ShelterDbEntity, ShelterEntity> {

    override fun mapFromCached(type: ShelterDbEntity): ShelterEntity {
        return ShelterEntity(type.country, type.longitude, type.name, type.phone,
                type.state, type.address2, type.email, type.city, type.zip, type.fax, type.latitude,
                type.id, type.address1)
    }

    override fun mapToCached(type: ShelterEntity): ShelterDbEntity {
        return ShelterDbEntity(null, type.country, type.longitude, type.name, type.phone,
                type.state, type.address2, type.email, type.city, type.zip, type.fax, type.latitude,
                type.id, type.address1)
    }

}