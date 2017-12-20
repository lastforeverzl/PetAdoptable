package com.zackyzhang.petadoptable.ui.mapper

import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.ui.model.PetViewModel
import javax.inject.Inject

/**
 * Map a [PetView] to and from a [PetViewModel] instance when data is moving between
 * this layer and the Domain layer
 */
open class PetMapper @Inject constructor() : Mapper<PetViewModel, PetView> {

    /**
     * Map a [PetView] instance to a [PetViewModel] instance
     */
    override fun mapToViewModel(type: PetView): PetViewModel {
        return PetViewModel(type.status, type.cityState, type.age, type.size, type.medias, type.id, type.breeds,
                type.name, type.sex, type.description, type.mix, type.shelterId, type.lastUpdate, type.animal)
    }

}
