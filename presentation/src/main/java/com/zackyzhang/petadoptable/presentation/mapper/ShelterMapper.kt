package com.zackyzhang.petadoptable.presentation.mapper

import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.petadoptable.presentation.model.ShelterView
import javax.inject.Inject

/**
 * Map a [ShelterView] to and from a [Shelter] instance when data is moving between
 * this layer and the Domain layer
 */
open class ShelterMapper @Inject constructor() : Mapper<ShelterView, Shelter> {

    /**
     * Map a [Shelter] instance to a [ShelterView] instance
     */
    override fun mapToView(type: Shelter): ShelterView {
        return ShelterView(type.country, type.longitude, type.name, type.phone, type.state, type.address2,
                type.email, type.city, type.zip, type.fax, type.latitude, type.id, type.address1)
    }

}