package com.zackyzhang.shelteradoptable.ui.mapper

import com.zackyzhang.petadoptable.presentation.model.ShelterView
import com.zackyzhang.petadoptable.ui.mapper.Mapper
import com.zackyzhang.petadoptable.ui.model.ShelterViewModel
import javax.inject.Inject

/**
 * Map a [ShelterView] to and from a [ShelterViewModel] instance when data is moving between
 * this layer and the Domain layer
 */
open class ShelterMapper @Inject constructor() : Mapper<ShelterViewModel, ShelterView> {

    /**
     * Map a [ShelterView] instance to a [ShelterViewModel] instance
     */
    override fun mapToViewModel(type: ShelterView): ShelterViewModel {
        val address = "${type.address1} ${type.address2} ${type.city} ${type.state} ${type.zip} ${type.country}"
        return ShelterViewModel(type.id, type.name, type.phone, type.email, type.latitude,
                type.longitude, type.fax, address)
    }

}